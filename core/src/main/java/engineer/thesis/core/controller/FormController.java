package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
public class FormController {

    @Autowired
    private IFormService formService;

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllForms() {
        return new ResponseEntity<Object>(formService.getAllForms(), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.GET)
    public ResponseEntity<?> getForm(@PathVariable Long id) {
        return new ResponseEntity<Object>(formService.getFormById(id), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteForm(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(formService.deleteForm(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_NAME, method = RequestMethod.GET)
    public ResponseEntity<?> getFormsByName(@PathVariable String name) {
        return new ResponseEntity<Object>(formService.getFormsByName(name), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_OWNER_ID, method = RequestMethod.GET)
    public ResponseEntity<?> getFormsByOwnerId(@PathVariable Long ownerId) {
        return new ResponseEntity<Object>(formService.getFormsByOwnerId(ownerId), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.POST)
    public ResponseEntity<?> saveForm(@RequestBody @Valid FormDTO form) {
        return new ResponseEntity<Object>(formService.saveForm(form), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.PUT)
    public ResponseEntity<?> saveForm(@PathVariable Long id, @RequestBody @Valid FormDTO form) {
        try {
            return new ResponseEntity<Object>(formService.updateForm(id, form), HttpStatus.OK);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
