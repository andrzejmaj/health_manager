package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
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
    public Optional<User> registerNewUser(RegisterRequest registerRequest, UserRole userRole) {

        if (emailExists(registerRequest.getEmail())) {
            return Optional.empty();
        }

        final User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);

        return Optional.of(user);
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
    public String checkReceivedToken(long id, String token) {
        PasswordResetToken passwordToken =
                passwordTokenRepository.findByToken(token);
        if ((passwordToken == null) || (passwordToken.getUser()
                .getId() != id)) {
            return "INVALID_TOKEN";
        }

        Calendar cal = Calendar.getInstance();
        if ((passwordToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            return "EXPIRED";
        }

        User user = passwordToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
