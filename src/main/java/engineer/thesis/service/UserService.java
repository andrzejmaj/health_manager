package engineer.thesis.service;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.repository.PasswordResetTokenRepository;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.PasswordResetToken;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<User> registerNewUser(RegisterRequest registerRequest) {

        if (emailExists(registerRequest.getEmail())) {
            return Optional.empty();
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.PATIENT);

        return Optional.of(userRepository.save(user));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(resetToken);
    }

    @Override
    public Boolean isResetPasswordTokenValid(long id, String token) {
        PasswordResetToken passwordToken =
                passwordTokenRepository.findByToken(token);
        if ((passwordToken == null) || (passwordToken.getUser()
                .getId() != id)) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        if ((passwordToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return false;
        }

        User user = passwordToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return true;
    }

    @Override
    public void changeUserPassword(User user, String password) {
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
}
