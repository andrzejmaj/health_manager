package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.service.IMedicalCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
public class MedicalCheckupController {

    @Autowired
    private IMedicalCheckupService medicalCheckupService;

    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP, method = RequestMethod.GET)
    public ResponseEntity<?> getAllMedicalCheckupsForPatient(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(medicalCheckupService.getPatientCheckups(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP, method = RequestMethod.POST)
    public ResponseEntity<?> saveMedicalCheckupForPatient(@PathVariable Long id, @RequestBody MedicalCheckupDTO medicalCheckupDTO) {
        try {
            return new ResponseEntity<>(medicalCheckupService.saveMedicalCheckup(id, medicalCheckupDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP, method = RequestMethod.PUT)
    public ResponseEntity<?> updateMedicalCheckupForPatient(@PathVariable Long id, @RequestBody MedicalCheckupDTO medicalCheckupDTO) {
        try {
            return new ResponseEntity<>(medicalCheckupService.updateMedicalCheckup(id, medicalCheckupDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
