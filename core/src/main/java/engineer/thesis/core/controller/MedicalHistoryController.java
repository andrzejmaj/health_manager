package engineer.thesis.core.controller;

import engineer.thesis.core.model.dto.MedicalHistoryDTO;
import engineer.thesis.core.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

@RestController
public class MedicalHistoryController {

    @Autowired
    MedicalHistoryService medicalHistoryService;

    @RequestMapping(path = RequestMappings.HISTORY.HISTORY, method = RequestMethod.GET)
    public ResponseEntity<?> getAllForPatientFromPeriod(@PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")  Date end) {
        try {
            return new ResponseEntity<>(medicalHistoryService.getAllByPatientIdFromPeriod(id, start, end), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.HISTORY.HISTORY, method = RequestMethod.POST)
    public ResponseEntity<?> save(@PathVariable Long id, @RequestBody MedicalHistoryDTO medicalHistoryDTO) {
        try {
            return new ResponseEntity<>(medicalHistoryService.save(id, medicalHistoryDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.HISTORY.HISTORY_UPDATE, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody MedicalHistoryDTO medicalHistoryDTO) {
        try {
            return new ResponseEntity<>(medicalHistoryService.update(medicalHistoryDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
