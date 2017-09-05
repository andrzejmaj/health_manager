package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalCheckupDTO;
import engineer.thesis.core.service.Interface.IMedicalCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalCheckupController {

    @Autowired
    private IMedicalCheckupService medicalCheckupService;

//    @RequestMapping(path = RequestMappings.CHECKUP.PATIENT_CHECKUP, method = RequestMethod.GET)
//    public ResponseEntity<?> getAll(@PathVariable Long patientId) {
//        try {
//            return new ResponseEntity<>(medicalCheckupService.getPatientCheckups(patientId), HttpStatus.OK);
//        } catch (NoSuchElementExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @RequestMapping(path = RequestMappings.CHECKUP.PATIENT_CHECKUP, method = RequestMethod.POST)
//    public ResponseEntity<?> save(@PathVariable Long patientId, @RequestBody MedicalCheckupDTO medicalCheckupDTO) {
//        try {
//            return new ResponseEntity<>(medicalCheckupService.saveMedicalCheckup(patientId, medicalCheckupDTO), HttpStatus.OK);
//        } catch (NoSuchElementExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (DataIntegrityException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(path = RequestMappings.CHECKUP.PATIENT_CHECKUP, method = RequestMethod.PUT)
//    public ResponseEntity<?> updateMedicalCheckupForPatient(@PathVariable Long patientId, @RequestBody MedicalCheckupDTO medicalCheckupDTO) {
//        try {
//            return new ResponseEntity<>(medicalCheckupService.updateMedicalCheckup(patientId, medicalCheckupDTO), HttpStatus.OK);
//        } catch (NoSuchElementExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (DataIntegrityException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(path = RequestMappings.CHECKUP.CHECKUP_ID, method = RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try {
//            medicalCheckupService.delete(id);
//            return new  ResponseEntity<>(true, HttpStatus.OK);
//        } catch (NoSuchElementExistsException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
}