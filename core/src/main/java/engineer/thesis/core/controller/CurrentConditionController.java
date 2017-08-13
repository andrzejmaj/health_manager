package engineer.thesis.core.controller;

import engineer.thesis.core.exception.DataIntegrityException;
import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.CurrentConditionDTO;
import engineer.thesis.core.service.Interface.ICurrentConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrentConditionController {

    @Autowired
    private ICurrentConditionService currentConditionService;

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long patientId) {
        try {
            return new ResponseEntity<>(currentConditionService.getPatientCondition(patientId), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.POST)
    public ResponseEntity<?> save(@PathVariable Long patientId, @RequestBody CurrentConditionDTO currentConditionDTO) {
        try {
            return new ResponseEntity<>(currentConditionService.savePatientCondition(patientId, currentConditionDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long patientId, @RequestBody CurrentConditionDTO currentConditionDTO) {
        try {
            return new ResponseEntity<>(currentConditionService.updatePatientCondition(patientId, currentConditionDTO), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long patientId, @PathVariable Long id){
        try {
            currentConditionService.delete(patientId, id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
