package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.service.Interface.IMedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class MedicalHistoryController {

    @Autowired
    private IMedicalHistoryService medicalHistoryService;

    @RequestMapping(path = RequestMappings.HISTORY.PATIENT_HISTORY, method = RequestMethod.GET)
    public ResponseEntity<?> getAllForPatientFromPeriod(@PathVariable Long patientId,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        try {
            return new ResponseEntity<>(medicalHistoryService.getAllByPatientIdFromPeriod(patientId, start, end), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.HISTORY.PATIENT_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<?> save(@PathVariable Long patientId, @RequestBody @Valid MedicalHistoryDTO medicalHistoryDTO) {
        try {
            return new ResponseEntity<>(medicalHistoryService.save(patientId, medicalHistoryDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.HISTORY.PATIENT_HISTORY_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid MedicalHistoryDTO medicalHistoryDTO) {
        try {
            return new ResponseEntity<>(medicalHistoryService.update(medicalHistoryDTO, id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.HISTORY.PATIENT_HISTORY_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            medicalHistoryService.delete(id);
            return new ResponseEntity<>("Successfully deleted history record " + id, HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
