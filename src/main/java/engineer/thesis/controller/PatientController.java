package engineer.thesis.controller;

import engineer.thesis.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientController {

    @Autowired
    protected IPatientService patientService;

    @RequestMapping(value="/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
//        patientRepository.findAll();
        return null;
    }

    @RequestMapping(value="/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@RequestParam("id") Long id) {
        patientService.findById(id);
        return null;
    }

    @RequestMapping(value="/patients/{pesel}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPesel(@RequestParam("pesel") String pesel) {
        patientService.findByPesel(pesel);
        return null;
    }





}
