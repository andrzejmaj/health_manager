package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.Account;
import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.repository.AccountRepository;
import engineer.thesis.core.service.Interface.BaseService;
import engineer.thesis.core.service.Interface.IAccountService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService extends BaseService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public PersonalDetailsDTO savePersonalDetails(Long accountId, PersonalDetailsDTO personalDetailsDTO) {

        Optional<Account> personalAccount = Optional.ofNullable(accountRepository.findOne(accountId));

        if (!personalAccount.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }

        personalAccount.get().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(personalAccount.get()).getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO getPersonalDetails(Long accountId) {

        Optional<Account> personalAccount = Optional.ofNullable(accountRepository.findOne(accountId));

        if (!personalAccount.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }

        return objectMapper.convert(personalAccount.get().getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public AccountDTO saveNewAccount(AccountDTO accountDTO) throws AlreadyExistsException {
        if (doesAccountExist(accountDTO.getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account already exists");
        }
        return objectMapper.convert(accountRepository.save(objectMapper.convert(accountDTO, Account.class)), AccountDTO.class);
    }

    @Override
    public String deleteAccount(Long id) {
        if (!doesAccountExist(id)) {
            throw new NoSuchElementException("Account not found");
        }
        accountRepository.delete(id);
        return "Account " + id + " deleted successfully";
    }

    @Override
    public Long getAccountIdByUserId(Long id) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findByUser_Id(id));
        if (!account.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }
        return account.get().getId();
    }

    @Override
    public String saveProfilePicture(Long id, MultipartFile userImage) {

        Account account = accountRepository.findOne(id);

        if (account == null) {
            throw new NoSuchElementException("Account not found");
        }

        String imagePath = fileService.saveProfilePicture(id, userImage);

        account.setImageUrl(imagePath);

        accountRepository.save(account);

        return "Image changed successfully";
    }

    @Override
    public FileSystemResource getProfilePicture(Long id) {

        if (!accountRepository.exists(id)) {
            throw new NoSuchElementException("Account not found");
        }

        return fileService.findProfilePicture(id);

    }

    @Override
    public String getMyRole() {
        return getCurrentUser().getUserRole().toString();
    }

    protected Boolean doesAccountExist(Long id) {
        return Optional.ofNullable(accountRepository.findOne(id)).isPresent();
    }

    protected Boolean doesAccountExist(String pesel) {
        return Optional.ofNullable(accountRepository.findByPersonalDetails_Pesel(pesel)).isPresent();
    }

}
