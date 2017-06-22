package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    Optional<User> findByEmail(String email);

    Optional<User> registerNewUser(RegisterRequest registerRequest);

    Optional<User> updateUser(UserDTO userDTO);

    void createPasswordResetTokenForUser(User user, String token);

    Boolean isResetPasswordTokenValid(long id, String token);

    void changeUserPassword(String email, String password);
}
