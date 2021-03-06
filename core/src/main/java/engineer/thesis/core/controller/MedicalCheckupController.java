package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import engineer.thesis.core.validator.PutValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalCheckupController {

    @Autowired
    private IMedicalCheckupService medicalCheckupService;

    @RequestMapping(path = RequestMappings.CHECKUP.PATIENT_CHECKUPS, method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody @Valid MedicalCheckupDTO medicalCheckupDTO) {
        try {
            return new ResponseEntity<>(medicalCheckupService.saveMedicalCheckup(medicalCheckupDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@Validated(PutValidationGroup.class) @RequestBody MedicalCheckupDTO medicalCheckupDTO, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(medicalCheckupService.updateMedicalCheckup(id, medicalCheckupDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            medicalCheckupService.delete(id);
            return new ResponseEntity<>("Successfully deleted checkup" + id, HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
