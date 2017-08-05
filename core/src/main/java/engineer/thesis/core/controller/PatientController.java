package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.service.IPatientService;
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

    @RequestMapping(path = "/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        System.out.println("PatientController - getAllPatients");
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = "/patients", method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@RequestBody PatientDTO patientDTO) {
        try {
            return new ResponseEntity<>(patientService.savePatient(patientDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = "/patients", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@RequestBody PatientDTO patientDTO) {
        System.out.println(patientDTO);
        try {
            return new ResponseEntity<>(patientService.updatePatient(patientDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
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

    @RequestMapping(path = "/patients/{id}/emergency", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientEmergency(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findByIdEmergency(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/patients/{id}/emergency", method = RequestMethod.POST)
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

    @RequestMapping(path = RequestMappings.PATIENTS.MEDICAL, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientMedicalInfo(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findIdMedicalInfo(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.MEDICAL, method = RequestMethod.POST)
    public ResponseEntity<?> saveMedicalInfo(@PathVariable(value = "id") Long id, @RequestBody MedicalInfoDTO medicalInfoDTO) {
        try {
            return new ResponseEntity<>(patientService.saveMedicalInfo(id, medicalInfoDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>("Medical info already exists", HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.MEDICAL, method = RequestMethod.PUT)
    public ResponseEntity<?> updateMedicalInfo(@PathVariable(value = "id") Long id, @RequestBody MedicalInfoDTO medicalInfoDTO) {
        try {
            return new ResponseEntity<>(patientService.updateMedicalInfo(id, medicalInfoDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }


}

