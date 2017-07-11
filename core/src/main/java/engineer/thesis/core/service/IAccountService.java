package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {

    String deleteAccount(Long id);

    Long getAccountIdByUserId(Long id);

    AccountDTO saveNewAccount(AccountDTO accountDTO) throws AlreadyExistsException;

    PersonalDetailsDTO getPersonalDetails(Long accountId);

    PersonalDetailsDTO savePersonalDetails(Long accountId, PersonalDetailsDTO personalDetailsDTO);
}
