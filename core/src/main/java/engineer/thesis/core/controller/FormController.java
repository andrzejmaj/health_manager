package engineer.thesis.core.controller;

import engineer.thesis.core.model.dto.FormDTO;
import engineer.thesis.core.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class FormController {

    @Autowired
    private IFormService formService;

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllForms() {
        System.out.println("FormController - getAllForms");
        return new ResponseEntity<Object>(formService.getAllForms(), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.GET)
    public ResponseEntity<?> getForm(@PathVariable Long id) {
        System.out.println("FormController - getForm");
        return new ResponseEntity<Object>(formService.getFormById(id), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORM, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteForm(@PathVariable Long id) {

        System.out.println("FormController - deleteForm");

        try {
            return new ResponseEntity<Object>(formService.deleteForm(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_NAME, method = RequestMethod.GET)
    public ResponseEntity<?> getFormsByName(@PathVariable String name) {
        System.out.println("FormController - getFormsByName");
        return new ResponseEntity<Object>(formService.getFormsByName(name), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS_OWNER_ID, method = RequestMethod.GET)
    public ResponseEntity<?> getFormsByOwnerId(@PathVariable Long ownerId) {
        System.out.println("FormController - getFormsByOwnerId");
        return new ResponseEntity<Object>(formService.getFormsByOwnerId(ownerId), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.FORMS.FORMS, method = RequestMethod.POST)
    public ResponseEntity<?> saveForm(@RequestBody FormDTO form) {
        System.out.println("FormController - saveForm");
        return new ResponseEntity<Object>(formService.saveForm(form), HttpStatus.OK);
    }

}
