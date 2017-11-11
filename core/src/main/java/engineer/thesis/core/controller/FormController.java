package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.DefaultValuesSetDTO;
import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.service.IFormService;
import engineer.thesis.core.validator.PostValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        try {
            return new ResponseEntity<Object>(formService.getFormById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.OK);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.GET, params = "name")
    public ResponseEntity<?> getFormsByName(@RequestParam String name) {
        return new ResponseEntity<Object>(formService.getFormsByName(name), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.POST)
    public ResponseEntity<?> saveForm(@RequestBody @Validated(PostValidationGroup.class) FormDTO form) {
        try {
            return new ResponseEntity<Object>(formService.saveForm(form), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.PUT)
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody @Valid FormDTO form) {
        try {
            return new ResponseEntity<Object>(formService.updateForm(id, form), HttpStatus.OK);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteForm(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(formService.deleteForm(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM_DEFAULTS, method = RequestMethod.GET)
    public ResponseEntity<?> getDefaultValues(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(formService.getDefaultValues(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM_DEFAULTS, method = RequestMethod.POST)
    public ResponseEntity<?> saveDefaultValues(@PathVariable Long id, @RequestBody @Valid DefaultValuesSetDTO defaultValuesSetDTO) {
        try {
            return new ResponseEntity<Object>(formService.saveDefaultValues(id, defaultValuesSetDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_DEFAULTS_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> updateDefaultValues(@PathVariable Long id, @RequestBody @Valid DefaultValuesSetDTO defaultValuesSetDTO) {
        try {
            return new ResponseEntity<Object>(formService.updateDefaultValues(id, defaultValuesSetDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_DEFAULTS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDefaultValues(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(formService.deleteDefaultSet(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
