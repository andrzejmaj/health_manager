package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.exception.TokenExpiredException;
import engineer.thesis.model.User;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.security.model.RegisterRequest;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.Optional;

@Service
public interface IUserService {

    PersonalDetailDTO savePersonalDetails(PersonalDetailDTO personalDetailDTO, Long userId) throws NotFoundException, NotBoundException;

    String registerNewUser(RegisterRequest registerRequest) throws AlreadyExistsException;

    UserDTO updateUser(UserDTO userDTO);

    String changeUserPassword(String email, String password);

    String changeUserPasswordWithToken(String email, String token, String password) throws TokenExpiredException;

    String updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);

    PersonalDetailDTO getPersonalDetails(Long id) throws NotFoundException, NotBoundException;
}
