package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.service.Interface.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PatientController {

    @Autowired
    protected IPatientService patientService;

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS_ID, method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable(value = "id") Long id) {
        try {

            return new ResponseEntity<>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientEmergency(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findByIdEmergency(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.POST)
    public ResponseEntity<?> saveEmergencyContact(@PathVariable(value = "id") Long id,
                                                  @RequestBody PersonalDetailsDTO emergencyContact) {
        try {
            return new ResponseEntity<>(patientService.saveEmergencyContact(id, emergencyContact), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = "pesel")
    public ResponseEntity<?> getPatientByPesel(@RequestParam String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = "lastName")
    public ResponseEntity<?> getPatientsByLastName(@RequestParam String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.POST)
    public ResponseEntity<?> registerNewPatient(@RequestBody PatientDTO patientDTO) {
        try {
            return new ResponseEntity<>(patientService.registerNewPatient(patientDTO), HttpStatus.OK);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}

