package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Kamil on 2017-04-08.
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<User> registerNewUser(RegisterRequest registerRequest) {

        if (emailExists(registerRequest.getEmail())) {
            return Optional.empty();
        }

        final User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.ROLE_PATIENT);

        userRepository.save(user);

        return Optional.of(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
