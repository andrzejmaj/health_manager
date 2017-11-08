package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.entity.Account;
import engineer.thesis.core.model.entity.PersonalDetails;
import engineer.thesis.core.model.entity.User;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public interface IAccountService {

    String deleteAccount(UUID uuid) throws NoSuchElementExistsException;

    PersonalDetailsDTO getPersonalDetails(UUID uuid) throws NoSuchElementExistsException;

    PersonalDetailsDTO getMyPersonalDetails(Long id) throws NoSuchElementExistsException;

    PersonalDetailsDTO savePersonalDetails(UUID uuid, PersonalDetailsDTO personalDetailsDTO) throws NoSuchElementExistsException;

    PersonalDetailsDTO saveMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws NoSuchElementExistsException, AlreadyExistsException;

    FileSystemResource getProfilePicture(UUID uuid) throws NoSuchElementExistsException;

    String saveProfilePicture(UUID uuid, MultipartFile userImage) throws NoSuchElementExistsException;

    Account createAccount(PersonalDetails personalDetails, User user) throws AlreadyExistsException;

    boolean checkExistence(String pesel);
}
