package engineer.thesis.service;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.Account;
import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.AccountDTO;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PersonalDetailDTO savePersonalDetails(Long accountId, PersonalDetailDTO personalDetailsDTO) {

        Optional<Account> personalAccount = Optional.ofNullable(accountRepository.findOne(accountId));

        if (!personalAccount.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }

        personalAccount.get().setPersonalDetails(convertPersonalDetailsToEntity(personalDetailsDTO));

        accountRepository.save(personalAccount.get());

        return personalDetailsDTO;
    }

    @Override
    public PersonalDetailDTO getPersonalDetails(Long accountId) {

        Optional<Account> personalAccount = Optional.ofNullable(accountRepository.findOne(accountId));

        if (!personalAccount.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }

        return convertPersonalDetailsToDTO(personalAccount.get().getPersonalDetails());
    }

    @Override
    public AccountDTO saveNewAccount(AccountDTO accountDTO) throws AlreadyExistsException {
        if (doesAccountExist(accountDTO.getPersonalDetails().getPesel())) {
            throw new AlreadyExistsException("Account already exists");
        }
        return convertAccountToDTO(accountRepository.save(convertAccountToEntity(accountDTO)));
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
        if (account.isPresent()) {
            throw new NoSuchElementException("Account not found");
        }
        return account.get().getId();
    }

    protected Boolean doesAccountExist(Long id) {
        return Optional.ofNullable(accountRepository.findOne(id)).isPresent();
    }

    protected Boolean doesAccountExist(String pesel) {
        return Optional.ofNullable(accountRepository.findByPersonalDetails_Pesel(pesel)).isPresent();
    }

    private AccountDTO convertAccountToDTO(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }

    private Account convertAccountToEntity(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, Account.class);
    }

    private PersonalDetailDTO convertPersonalDetailsToDTO(PersonalDetails personalDetails) {
        return modelMapper.map(personalDetails, PersonalDetailDTO.class);
    }

    private PersonalDetails convertPersonalDetailsToEntity(PersonalDetailDTO personalDetailDTO) {
        return modelMapper.map(personalDetailDTO, PersonalDetails.class);
    }
}
