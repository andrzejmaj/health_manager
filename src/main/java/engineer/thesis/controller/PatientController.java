package engineer.thesis.controller;

import engineer.thesis.model.Patient;
import engineer.thesis.model.dto.ErrorDTO;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PatientController {

    @Autowired
    protected IPatientService patientService;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<Object>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new ErrorDTO("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/patients/{pesel}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPesel(@RequestParam("pesel") String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(new Error("Patient not found"), HttpStatus.NOT_FOUND);
        }
    }
}
