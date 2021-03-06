package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.service.Interface.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(path = RequestMappings.ACCOUNTS.MY_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getMyPersonalDetails() {
        try {
            return new ResponseEntity<>(accountService.getMyPersonalDetails(getCurrentUser().getId()), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = RequestMappings.ACCOUNTS.MY_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> saveMyPersonalDetails(@RequestBody @Valid PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(accountService.saveMyPersonalDetails(getCurrentUser().getId(), personalDetails), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = RequestMappings.ACCOUNTS.MY_PERS_DETAILS, method = RequestMethod.PUT)
    public ResponseEntity<?> updateMyPersonalDetails(@RequestBody @Valid PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(accountService.updateMyPersonalDetails(getCurrentUser().getId(), personalDetails), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS_PICTURE, method = RequestMethod.GET)
    public ResponseEntity<?> getProfilePicture(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(accountService.getProfilePicture(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.ACCOUNTS.ACCOUNTS_PICTURE, method = RequestMethod.POST)
    public ResponseEntity<?> saveProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(accountService.saveProfilePicture(id, file), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }
}