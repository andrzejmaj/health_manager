package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.Account;
import engineer.thesis.core.model.PersonalDetails;
import engineer.thesis.core.model.User;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.repository.AccountRepository;
import engineer.thesis.core.repository.PersonalDetailsRepository;
import engineer.thesis.core.service.Interface.IAccountService;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private CustomObjectMapper objectMapper;

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Override
    public PersonalDetailsDTO getMyPersonalDetails(Long id) throws NoSuchElementExistsException {
        Account account = accountRepository.findByUser_Id(id);
        if (account == null) {
            throw new NoSuchElementExistsException("Account doesn't exist");
        }
        return objectMapper.convert(account.getPersonalDetails(), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO saveMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws AlreadyExistsException {
        Account account = accountRepository.findByUser_Id(id);

        if (account.getPersonalDetails() != null) {
            throw new AlreadyExistsException("Personal Details already exist");
        }

        personalDetails.setId(null);
        account.setPersonalDetails(objectMapper.convert(personalDetails, PersonalDetails.class));

        return objectMapper.convert(accountRepository.save(account), PersonalDetailsDTO.class);
    }

    @Override
    public PersonalDetailsDTO updateMyPersonalDetails(Long id, PersonalDetailsDTO personalDetails) throws NoSuchElementExistsException {
        Account account = accountRepository.findByUser_Id(id);
        if (account.getPersonalDetails() == null) {
            throw new NoSuchElementExistsException("Personal Details already exist");
        }

        personalDetails.setId(account.getPersonalDetails().getId());

        return objectMapper.convert(personalDetailsRepository.save(objectMapper.convert(personalDetails, PersonalDetails.class)), PersonalDetailsDTO.class);
    }

    @Override
    public FileSystemResource getProfilePicture(Long id) throws NoSuchElementExistsException {
        Account a = checkExistence(id);
        return fileService.findProfilePicture(a.getId());
    }

    @Override
    public String saveProfilePicture(Long id, MultipartFile userImage) throws NoSuchElementExistsException {

        Account account = checkExistence(id);

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
        account.setPersonalDetails(personalDetails);
        account.setUser(user);
        return account;
    }

    @Override
    public boolean checkExistence(String pesel) {
        Account account = accountRepository.findByPersonalDetails_Pesel(pesel);
        return account != null;
    }

    private Account checkExistence(Long id) throws NoSuchElementExistsException {
        return Optional.ofNullable(accountRepository.findOne(id)).orElseThrow(() -> new NoSuchElementExistsException("Account doesn't exist"));
    }

}
