package engineer.thesis.controller;

import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.ErrorDTO;
import engineer.thesis.model.dto.PatientDTO;
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

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        System.out.println("PatientController - getAllPatients");
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable(value = "id") Long id) {
        System.out.println("PatientController - getPatient");
        try {
            return new ResponseEntity<Object>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new ErrorDTO("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/patients/searchByPesel/{pesel}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPesel(@PathVariable("pesel") String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new Error("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/patients/searchByLastName", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientsByLastName(@RequestParam(value = "lastName", required = false) String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/patients/{id}/currentState", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientCurrentState(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<Object>(patientService.getPatientCurrentCondition(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new ErrorDTO("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/patients/{id}/medicalInformation", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientMedicalInformation(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<Object>(patientService.getPatientMedicalInformation(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new ErrorDTO("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }

}
