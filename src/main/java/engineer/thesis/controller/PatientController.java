package engineer.thesis.controller;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.ErrorDTO;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    protected IPatientService patientService;

    @RequestMapping(path = "/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        System.out.println("PatientController - getAllPatients");
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = "/patients}", method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@RequestBody PatientDTO personalDetailDTO) {

        try {
            return new ResponseEntity<>(patientService.savePatient(personalDetailDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(path = "/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable(value = "id") Long id) {
        try {

            return new ResponseEntity<>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/patients/pesel/{pesel}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPesel(@PathVariable("pesel") String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/patients/searchByLastName", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientsByLastName(@RequestParam(value = "lastName", required = false) String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


}
