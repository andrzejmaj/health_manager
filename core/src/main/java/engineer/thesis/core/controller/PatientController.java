package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PatientDetailsDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.service.Interface.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS_ID, method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = "pesel")
    public ResponseEntity<?> getPatientByPesel(@RequestParam String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS_EMAIL, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<Object>(patientService.findByEmail(email), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatientsShort() {
        return new ResponseEntity<Object>(patientService.findAllPatientsShort(), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<?> getAllPatientsShort(Pageable pageable) {
        return new ResponseEntity<Object>(patientService.findAllPatientsShort(pageable), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = "lastName")
    public ResponseEntity<?> getPatientsByLastName(@RequestParam String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@RequestBody PatientDTO patientDTO) {
        try {
            return new ResponseEntity<>(patientService.savePatient(patientDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientEmergency(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findEmergencyById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.EMERGENCY, method = RequestMethod.POST)
    public ResponseEntity<?> saveEmergencyContact(@PathVariable(value = "id") Long id,
                                                  @RequestBody @Valid PersonalDetailsDTO emergencyContact) {
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
                                                    @RequestBody @Valid PersonalDetailsDTO emergencyContact) {
        try {
            return new ResponseEntity<>(patientService.updateEmergency(id, emergencyContact), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<?> registerUserless(@RequestBody @Valid PatientDetailsDTO patientDetails) {
        try {
            return new ResponseEntity<>(patientService.register(patientDetails), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}

