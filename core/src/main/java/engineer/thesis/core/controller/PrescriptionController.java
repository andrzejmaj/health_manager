package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.PrescriptionDTO;
import engineer.thesis.core.service.Interface.IPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PrescriptionController {

    @Autowired
    private IPrescriptionService prescriptionService;

    @RequestMapping(path = RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS_ID, method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(prescriptionService.get(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS, method = RequestMethod.GET, params = "patientId")
    public ResponseEntity<?> getByPatientId(@RequestParam Long patientId) {
        try {
            return new ResponseEntity<Object>(prescriptionService.getByPatientId(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS, method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody @Valid PrescriptionDTO prescriptionDTO) {
        try {
            return new ResponseEntity<Object>(prescriptionService.save(prescriptionDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS_ID, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PrescriptionDTO prescriptionDTO) {
        try {
            return new ResponseEntity<Object>(prescriptionService.update(id, prescriptionDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(prescriptionService.delete(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
