package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.repository.PasswordResetTokenRepository;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.PasswordResetToken;
import engineer.thesis.security.model.RegisterRequest;
import engineer.thesis.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<User> registerNewUser(RegisterRequest registerRequest) {

        if (emailExists(registerRequest.getEmail())) {
            return Optional.empty();
        }

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setBirthDate(new Date());
        personalDetails.setBuildingNumber(4);
        personalDetails.setCity("Cracow");
        personalDetails.setCountry("Poland");
        personalDetails.setFirstName("Andrzej");
        personalDetails.setLastName("Maj");
        personalDetails.setStreet("Ulica");
        personalDetails.setFlatNumber(4);
        personalDetails.setPhoneNumber("123456789");
        personalDetails.setGender("Male");
        personalDetails.setPesel("1234567890");

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.PATIENT);
        user.setPersonalDetails(personalDetails);

        return Optional.of(userRepository.save(user));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        System.out.println("CREATE PASSWORD TOKEN FOR USER: " + resetToken);
        passwordTokenRepository.save(resetToken);
    }

    @Override
    public Boolean isResetPasswordTokenValid(String email, String token) {
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

    @Override
    public void changeUserPassword(String email, String password) {
        System.out.println("CHANGE PASSWORD" + email);
        User user = userRepository.findByEmail(email);
        System.out.println("FIND USER" + user);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(UserDTO userDTO) {
        Optional<User> user = Optional.of(userRepository.findByEmail(userDTO.getEmail()));
        if (user.isPresent()) {
            user.get().setEmail(userDTO.getEmail());
            user.get().setRole(UserRole.valueOf(userDTO.getRole()));
            user.get().setImageUrl(userDTO.getImgUrl());
            return Optional.of(userRepository.save(user.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateUserEmail(Long id, String newEmail) {
        Optional<User> user = Optional.of(userRepository.findOne(id));
        if (user.isPresent()) {
            user.get().setEmail(newEmail);
            return Optional.of(userRepository.save(user.get()));
        }
        return Optional.empty();
    }

    public boolean canPerformUserAction(Long id, SecurityUser currentUser) {
        return id.equals(currentUser.getId()) || currentUser.getUserRole() == UserRole.ADMIN;
    }
}
