package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.service.Interface.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = "lastName")
    public ResponseEntity<?> getPatientsByLastName(@RequestParam String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatientsShort() {
        return new ResponseEntity<Object>(patientService.findAllPatientsShort(), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.PATIENTS, method = RequestMethod.GET, params = {"page", "size"})
    public ResponseEntity<?> getAllPatientsShort(Pageable pageable) {
        return new ResponseEntity<Object>(patientService.findAllPatientsShort(pageable), HttpStatus.OK);
    }
}

