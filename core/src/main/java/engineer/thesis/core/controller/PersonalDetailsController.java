package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PersonalDetailsDTO;
import engineer.thesis.core.service.Interface.IPersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class PersonalDetailsController {

    @Autowired
    IPersonalDetailsService personalDetailsService;

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
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdateMine(personalDetailsDTO, true), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.MY_PERS_DETAILS, method = RequestMethod.PUT)
    public ResponseEntity<?> updateMine(@RequestBody PersonalDetailsDTO personalDetailsDTO) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdateMine(personalDetailsDTO, false), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(path = RequestMappings.PERS_DETAILS.PATIENT_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(personalDetailsService.get(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.PATIENT_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@PathVariable Long patientId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdate(patientId, personalDetails, true), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.PATIENT_PERS_DETAILS, method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatient(@PathVariable Long patientId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdate(patientId, personalDetails, false), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.DOCTOR_PERS_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getDoctor(@PathVariable Long doctorId) {
        try {
            return new ResponseEntity<>(personalDetailsService.getDoctor(doctorId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.DOCTOR_PERS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> saveDoctor(@PathVariable Long doctorId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdateDoctor(doctorId, personalDetails, true), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PERS_DETAILS.DOCTOR_PERS_DETAILS, method = RequestMethod.PUT)
    public ResponseEntity<?> updateDoctor(@PathVariable Long doctorId, @RequestBody PersonalDetailsDTO personalDetails) {
        try {
            return new ResponseEntity<>(personalDetailsService.saveOrUpdateDoctor(doctorId, personalDetails, false), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
