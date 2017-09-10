package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.service.Implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS, method = RequestMethod.POST)
    public ResponseEntity<?> saveNewAccount(@RequestBody AccountDTO accountDTO) {
        try {
            return new ResponseEntity<>(accountService.saveNewAccount(accountDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(accountService.deleteAccount(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = {RequestMappings.ACCOUNTS.PERS_DETAILS, RequestMappings.ACCOUNTS.MY_PERS_DETAILLS}, method = RequestMethod.GET)
    public ResponseEntity<?> getPersonalDetails(@PathVariable Optional<Long> id) {
        try {
            Long accountId = id.isPresent() ? id.get() : accountService.getAccountIdByUserId(getCurrentUser().getId());
            return new ResponseEntity<>(accountService.getPersonalDetails(accountId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = {RequestMappings.ACCOUNTS.PERS_DETAILS, RequestMappings.ACCOUNTS.MY_PERS_DETAILLS}, method = RequestMethod.POST)
    public ResponseEntity<?> savePersonalDetails(@PathVariable Optional<Long> id,
                                                 @RequestBody PersonalDetailsDTO personalDetails
    ) {
        try {
            Long accountId = id.isPresent() ? id.get() : accountService.getAccountIdByUserId(getCurrentUser().getId());
            return new ResponseEntity<>(accountService.savePersonalDetails(accountId, personalDetails), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS_PICTURE, method = RequestMethod.POST)
    public ResponseEntity<?> saveProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(accountService.saveProfilePicture(id, file), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS_PICTURE, method = RequestMethod.GET)
    public ResponseEntity<?> getProfilePicture(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(accountService.getProfilePicture(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }

}