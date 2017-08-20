package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.service.Interface.IPersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonalDetailsController {

    @Autowired
    private IPersonalDetailsService personalDetailsService;

    @RequestMapping(path = RequestMappings.PERS_DETAILS.MY_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getMine() {
        try {
            return new ResponseEntity<>(personalDetailsService.getMine(), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.MY_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> saveMine(@RequestBody PersonalDetailsDTO personalDetailsDTO) {
        return new ResponseEntity<>(personalDetailsService.saveOrUpdateMine(personalDetailsDTO), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.PATIENT_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(personalDetailsService.getPatient(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.PATIENT_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@PathVariable Long patientId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdatePatient(patientId, personalDetails), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.DOCTOR_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getDoctor(@PathVariable Long doctorId) {
        try {
            return new ResponseEntity<>(personalDetailsService.getDoctor(doctorId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.DOCTOR_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> saveDoctor(@PathVariable Long doctorId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdateDoctor(doctorId, personalDetails), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
