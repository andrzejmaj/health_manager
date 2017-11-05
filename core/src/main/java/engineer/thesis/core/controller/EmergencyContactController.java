package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.EmergencyContactDTO;
import engineer.thesis.core.service.Implementation.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmergencyContactController {

    @Autowired
    private PatientService patientService;

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientEmergencyContact(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findEmergencyById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.POST)
    public ResponseEntity<?> saveEmergencyContact(@PathVariable(value = "id") Long id,
                                                  @RequestBody @Valid EmergencyContactDTO emergencyContact) {
        try {
            return new ResponseEntity<>(patientService.saveEmergency(id, emergencyContact), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.PUT)
    public ResponseEntity<?> updateEmergencyContact(@PathVariable(value = "id") Long id,
                                                    @RequestBody @Valid EmergencyContactDTO emergencyContact) {
        try {
            return new ResponseEntity<>(patientService.updateEmergency(id, emergencyContact), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
