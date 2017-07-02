package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.model.dto.ResponseDTO;
import engineer.thesis.model.dto.UserDTO;
import engineer.thesis.security.model.RegisterRequest;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.util.Optional;

@Service
public interface IUserService {

    ResponseDTO savePersonalDetails(PersonalDetailDTO personalDetailDTO, Long userId) throws NotFoundException, NotBoundException;

    ResponseDTO registerNewUser(RegisterRequest registerRequest);

    ResponseDTO updateUser(UserDTO userDTO);

    ResponseDTO changeUserPassword(String email, String password);

    ResponseDTO changeUserPasswordWithToken(String email, String token, String password);

    ResponseDTO updateUserEmail(Long id, String newEmail);

    Optional<User> findByEmail(String email);

    ResponseDTO getPersonalDetails(Long id) throws NotFoundException, NotBoundException;
}
