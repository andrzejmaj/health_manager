package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.service.Interface.IMedicalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalInfoController {


    @Autowired
    private IMedicalInfoService medicalInfoService;

    @RequestMapping(path = RequestMappings.MEDICAL.PATIENT_MEDICAL, method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(medicalInfoService.findByPatientId(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.MEDICAL.PATIENT_MEDICAL, method = RequestMethod.POST)
    public ResponseEntity<?> save(@PathVariable Long patientId, @RequestBody @Valid MedicalInfoDTO medicalInfoDTO) {
        try {
            return new ResponseEntity<>(medicalInfoService.save(patientId, medicalInfoDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.MEDICAL.PATIENT_MEDICAL_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long patientId, @PathVariable Long id, @RequestBody @Valid MedicalInfoDTO medicalInfoDTO) {
        try {
            return new ResponseEntity<>(medicalInfoService.update(patientId, id, medicalInfoDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.MEDICAL.PATIENT_MEDICAL, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long patientId) {
        try {
            medicalInfoService.delete(patientId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
