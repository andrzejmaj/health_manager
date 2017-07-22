package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.TokenExpiredException;
import engineer.thesis.core.model.User;
import engineer.thesis.core.model.dto.UserDTO;
import engineer.thesis.core.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    String registerNewUser(RegisterRequest registerRequest) throws AlreadyExistsException;

    UserDTO updateUser(UserDTO userDTO);

    String changeUserPassword(String email, String password);

    String changeUserPasswordWithToken(String email, String token, String password) throws TokenExpiredException;

    String updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);

}