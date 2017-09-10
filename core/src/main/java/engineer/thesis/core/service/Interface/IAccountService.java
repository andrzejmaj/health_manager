package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IAccountService {

    String deleteAccount(Long id);

    Long getAccountIdByUserId(Long id);

    AccountDTO saveNewAccount(AccountDTO accountDTO) throws AlreadyExistsException;

    PersonalDetailsDTO getPersonalDetails(Long accountId);

    PersonalDetailsDTO savePersonalDetails(Long accountId, PersonalDetailsDTO personalDetailsDTO);

    String saveProfilePicture(Long id, MultipartFile file);

    FileSystemResource getProfilePicture(Long id);
}
