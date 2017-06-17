package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    Optional<User> findByEmail(String email);

    Optional<User> registerNewUser(RegisterRequest registerRequest);

    void createPasswordResetTokenForUser(User user, String token);

    String checkReceivedToken(long id, String token);

    void changeUserPassword(User user, String password);
}
