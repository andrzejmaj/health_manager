package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.TokenExpiredException;
import engineer.thesis.core.model.dto.RegisterRequestDTO;
import engineer.thesis.core.model.dto.ResetPasswordDTO;
import engineer.thesis.core.model.dto.SpecializationDTO;
import engineer.thesis.core.model.dto.UserDTO;
import engineer.thesis.core.model.entity.User;
import engineer.thesis.core.model.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public interface IUserService {

    void registerUserByRole(RegisterRequestDTO request, UserRole userRole, Boolean isOnBehalf, SpecializationDTO specialization) throws AlreadyExistsException;

    UserDTO updateUser(UserDTO userDTO);

    String changeUserPassword(String email, String password);

    String changeUserPasswordWithToken(Long id, String token, String password) throws TokenExpiredException;

    String updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);

    ResetPasswordDTO resetUserPassword(String email) throws NoSuchElementException;
}
