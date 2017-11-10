package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Account;
import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.User;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IAccountService {

    PersonalDetailsDTO getMyPersonalDetails(Long id) throws NoSuchElementExistsException;

    PersonalDetailsDTO saveMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws AlreadyExistsException;

    PersonalDetailsDTO updateMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws NoSuchElementExistsException;

    FileSystemResource getProfilePicture(Long id) throws NoSuchElementExistsException;

    String saveProfilePicture(Long id, MultipartFile userImage) throws NoSuchElementExistsException;

    Account createAccount(PersonalDetails personalDetails, User user) throws AlreadyExistsException;

    boolean checkExistence(String pesel);
}
