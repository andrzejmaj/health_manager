package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.TokenExpiredException;
import engineer.thesis.core.model.User;
import engineer.thesis.core.model.UserRole;
import engineer.thesis.core.model.dto.UserDTO;
import engineer.thesis.core.model.dto.RegisterRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    void registerUserByRole(RegisterRequestDTO request, UserRole userRole, Boolean isOnBehalf) throws AlreadyExistsException;

    UserDTO updateUser(UserDTO userDTO);

    String changeUserPassword(String email, String password);

    String changeUserPasswordWithToken(Long id, String token, String password) throws TokenExpiredException;

    String updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);
}
