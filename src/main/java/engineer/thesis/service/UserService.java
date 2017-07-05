package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.model.dto.ResetPasswordDTO;
import engineer.thesis.model.dto.ResponseDTO;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.repository.PasswordResetTokenRepository;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.PasswordResetToken;
import engineer.thesis.security.model.RegisterRequest;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        System.out.println("GET PERSONAL DETAILS: " + id);

        User user = userRepository.findOne(id);

        System.out.println("GET USER: " + user);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (user.getPersonalDetails() == null) {
            throw new NotBoundException("User doesn't have personal details");
        }

        return personalDetailsService.mapToDTO(user.getPersonalDetails());
    }


    @Override
    public PersonalDetailDTO savePersonalDetails(PersonalDetailDTO personalDetailDTO, Long userId) throws NotFoundException  {

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
    public ResponseDTO updateUser(UserDTO userDTO) {

        ResponseDTO response = new ResponseDTO();
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDTO.getEmail()));

        if (!user.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("User not found in database");

        } else {
            user.get().setEmail(userDTO.getEmail());
            user.get().setRole(UserRole.valueOf(userDTO.getRole()));
            user.get().setImageUrl(userDTO.getImgUrl());

            response.setStatus(HttpStatus.OK);
            response.setMessage("User was successfully updated");
        }

        return response;
    }

    @Override
    public ResponseDTO changeUserPassword(String email, String password) {

        ResponseDTO response = new ResponseDTO();
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("User not found in database");

        } else {
            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());

            response.setStatus(HttpStatus.OK);
            response.setMessage("Password has been changed successfully");
        }

        return response;
    }

    @Override
    public ResponseDTO changeUserPasswordWithToken(String email, String token, String password) {

        ResponseDTO response = new ResponseDTO();
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("User not found in database");
        }

        else if (isResetPasswordTokenValid(email, token)) {
            response.setStatus(HttpStatus.FORBIDDEN);
            response.setMessage("Token is not valid");
        }

        else {

            user.get().setPassword(passwordEncoder.encode(password));
            userRepository.save(user.get());

            response.setStatus(HttpStatus.OK);
            response.setMessage("Password has been changed successfully");
        }

        return response;
    }


    @Override
    public ResponseDTO updateUserEmail(Long id, String newEmail) {

        ResponseDTO response = new ResponseDTO();
        Optional<User> user = Optional.ofNullable(userRepository.findOne(id));

        if (!user.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("User not found in database");

        } else {
            user.get().setEmail(newEmail);
            userRepository.save(user.get());
            response.setStatus(HttpStatus.OK);
            response.setMessage("Email updated successfully");
        }

        return response;
    }

    public ResponseDTO resetUserPassword(String email) {

        ResponseDTO response = new ResponseDTO();
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (!user.isPresent()) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("User not found in database");
        } else {
            String token = UUID.randomUUID().toString();

            //TODO:
            // add to reset password token table isActive
            // property and disable it after used
            createPasswordResetTokenForUser(user.get(), token);

            ResetPasswordDTO resetPassword = new ResetPasswordDTO();

            resetPassword.setEmail(user.get().getEmail());
            resetPassword.setUserId(user.get().getId());
            resetPassword.setToken(token);

            response.setStatus(HttpStatus.OK);
            response.setMessage("Token created successfully");
            response.setData(resetPassword);
        }

        return response;
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
}
