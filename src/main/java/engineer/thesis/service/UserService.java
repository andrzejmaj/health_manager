package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.exception.TokenExpiredException;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.model.dto.ResetPasswordDTO;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.repository.PasswordResetTokenRepository;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.PasswordResetToken;
import engineer.thesis.security.model.RegisterRequest;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PersonalDetailsService personalDetailsService;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public PersonalDetailDTO getPersonalDetails(Long id) throws NotFoundException, NotBoundException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (user.getPersonalDetails() == null) {
            throw new NotBoundException("User doesn't have personal details");
        }

        return personalDetailsService.mapToDTO(user.getPersonalDetails());
    }


    @Override
    public PersonalDetailDTO savePersonalDetails(PersonalDetailDTO personalDetailDTO, Long userId) throws NotFoundException {

        User user = userRepository.findOne(userId);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        user.setPersonalDetails(personalDetailsService.mapFromDTO(personalDetailDTO));

        userRepository.save(user);

        return personalDetailsService.mapToDTO(user.getPersonalDetails());
    }


    @Override
    public String registerNewUser(RegisterRequest registerRequest) throws AlreadyExistsException {

        if (userExists(registerRequest.getEmail())) {
            throw new AlreadyExistsException("User with such mail already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.ROLE_PATIENT);

        userRepository.save(user);

        return "User successfully registered";
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDTO.getEmail()));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setEmail(userDTO.getEmail());
        user.get().setRole(UserRole.valueOf(userDTO.getRole()));
        user.get().setImageUrl(userDTO.getImgUrl());

        return mapToDTO(user.get());
    }

    @Override
    public String changeUserPassword(String email, String password) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());

        return "Password has been changed successfully";
    }

    @Override
    public String changeUserPasswordWithToken(String email, String token, String password) throws TokenExpiredException, NoSuchElementException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        if (isResetPasswordTokenValid(email, token)) {
            throw new TokenExpiredException("Token is not valid");
        }

        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());

        return "Password has been changed successfully";
    }

    @Override
    public String updateUserEmail(Long id, String newEmail) {
        Optional<User> user = Optional.ofNullable(userRepository.findOne(id));

        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setEmail(newEmail);
        userRepository.save(user.get());

        return "Email updated successfully";
    }

    public ResetPasswordDTO resetUserPassword(String email) throws NoSuchElementException {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (!user.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        String token = UUID.randomUUID().toString();
        //TODO:
        // add to reset password token table isActive
        // property and disable it after used
        createPasswordResetTokenForUser(user.get(), token);
        ResetPasswordDTO resetPassword = new ResetPasswordDTO();
        resetPassword.setEmail(user.get().getEmail());
        resetPassword.setUserId(user.get().getId());
        resetPassword.setToken(token);

        return resetPassword;
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean userExists(Long id) {
        return userRepository.findOne(id) != null;
    }

    private Boolean isResetPasswordTokenValid(String email, String token) {
        System.out.println(email);
        PasswordResetToken passwordToken =
                passwordTokenRepository.findByToken(token);
        System.out.println(passwordToken.getUser().getEmail());
        if (!Objects.equals(passwordToken.getUser().getEmail(), email)) {
            System.out.println("Email does not match");
            return false;
        }

        Calendar cal = Calendar.getInstance();
        return (passwordToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) > 0;
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(resetToken);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                String.valueOf(user.getRole()),
                user.getImageUrl()
        );
    }
}
