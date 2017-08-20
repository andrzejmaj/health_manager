package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.AccountDTO;
import engineer.thesis.core.service.Implementation.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

}