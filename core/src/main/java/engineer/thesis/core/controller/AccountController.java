package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
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

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNT, method = RequestMethod.DELETE)
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

    private SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }

    @Autowired
    ApplicationContext c;

    @RequestMapping(path = "/beans")
    public String s(){
        return c.getBeanDefinitionNames().toString();

    }

}