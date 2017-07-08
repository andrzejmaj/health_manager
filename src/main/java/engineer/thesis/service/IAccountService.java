package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.dto.AccountDTO;
import engineer.thesis.model.dto.PersonalDetailDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

    String deleteAccount(Long id);

    Long getAccountIdByUserId(Long id);

    AccountDTO saveNewAccount(AccountDTO accountDTO) throws AlreadyExistsException;

    PersonalDetailDTO getPersonalDetails(Long accountId);

    PersonalDetailDTO savePersonalDetails(Long accountId, PersonalDetailDTO personalDetailsDTO);
}
