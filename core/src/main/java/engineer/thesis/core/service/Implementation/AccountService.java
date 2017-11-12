package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.entity.Account;
import engineer.thesis.core.model.entity.PersonalDetails;
import engineer.thesis.core.model.entity.User;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.repository.AccountRepository;
import engineer.thesis.core.service.Interface.IAccountService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Override
    public String deleteAccount(UUID uuid) throws NoSuchElementExistsException {
        Account account = checkExistence(uuid);
        accountRepository.delete(account);
        return "Account " + uuid + " deleted successfully";
    }

    @Override
    public PersonalDetailsDTO getPersonalDetails(UUID uuid) throws NoSuchElementExistsException {
        Account account = checkExistence(uuid);
        return objectMapper.convert(account.getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO getMyPersonalDetails(Long id) throws NoSuchElementExistsException {
        Account account = accountRepository.findByUser_Id(id);
        if (account == null) {
            throw new NoSuchElementExistsException("Account doesn't exist");
        }
        return objectMapper.convert(account.getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO savePersonalDetails(UUID uuid, PersonalDetailsDTO personalDetailsDTO) {

        Optional<Account> personalAccount = Optional.ofNullable(accountRepository.findByUuid(uuid));

        if (!personalAccount.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }

        personalDetailsDTO.setId(null);
        personalAccount.get().setPersonalDetails(objectMapper.convert(personalDetailsDTO, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(personalAccount.get()).getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws NoSuchElementExistsException, AlreadyExistsException {
        Account account = accountRepository.findByUser_Id(id);

        if (account.getPersonalDetails() != null) {
            throw new AlreadyExistsException("Personal Details already exist");
        }

        personalDetails.setId(null);
        account.setPersonalDetails(objectMapper.convert(personalDetails, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(account), PersonalDetailsDTO.class);
    }

    @Override
    public FileSystemResource getProfilePicture(UUID uuid) throws NoSuchElementExistsException {
        Account a = checkExistence(uuid);
        return fileService.findProfilePicture(a.getId());
    }

    @Override
    public String saveProfilePicture(UUID uuid, MultipartFile userImage) throws NoSuchElementExistsException {

        Account account = checkExistence(uuid);

        String imagePath = fileService.saveProfilePicture(account.getId(), userImage);

        account.setImageUrl(imagePath);

        accountRepository.save(account);

        return "Image changed successfully";
    }

    @Override
    public Account createAccount(PersonalDetails personalDetails, User user) throws AlreadyExistsException {
        if (checkExistence(personalDetails.getPesel())) {
            throw new AlreadyExistsException("Patient with such pesel already exists");
        }

        personalDetails.setId(null);

        Account account = new Account();
        account.setCreatedDate(new Date());
        account.setUuid(UUID.randomUUID());
        account.setPersonalDetails(personalDetails);
        account.setUser(user);
        return account;
    }

    @Override
    public boolean checkExistence(String pesel) {
        Account account = accountRepository.findByPersonalDetails_Pesel(pesel);
        return account != null;
    }

    private Account checkExistence(UUID uuid) throws NoSuchElementExistsException {
        return Optional.ofNullable(accountRepository.findByUuid(uuid)).orElseThrow(() -> new NoSuchElementExistsException("Account doesn't exist"));
    }

}
