package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.service.Interface.IMedicalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MedicalInfoController {


    @Autowired
    private IMedicalInfoService medicalInfoService;

    @RequestMapping(path = RequestMappings.MEDICAL_INFO.PATIENT_MEDICAL, method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(medicalInfoService.findByPatientId(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = RequestMappings.MEDICAL_INFO.PATIENT_MEDICAL, method = RequestMethod.POST)
    public ResponseEntity<?> saveOrUpdate(@PathVariable Long patientId, @RequestBody MedicalInfoDTO medicalInfoDTO) {
        try {
            return new ResponseEntity<>(medicalInfoService.saveOrUpdate(patientId, medicalInfoDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(path = RequestMappings.MEDICAL_INFO.PATIENT_MEDICAL, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long patientId) {
        try {
            medicalInfoService.delete(patientId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
