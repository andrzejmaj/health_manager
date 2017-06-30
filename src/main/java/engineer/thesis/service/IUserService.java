package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.ResponseDTO;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    ResponseDTO registerNewUser(RegisterRequest registerRequest);

    ResponseDTO updateUser(UserDTO userDTO);

    ResponseDTO changeUserPassword(String email, String password);

    ResponseDTO changeUserPasswordWithToken(String email, String token, String password);

    ResponseDTO updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);
}
