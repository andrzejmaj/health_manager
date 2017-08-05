package engineer.thesis.core.controller;

import engineer.thesis.core.model.dto.CurrentConditionDTO;
import engineer.thesis.core.service.ICurrentConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class CurrentConditionController {

    @Autowired
    private ICurrentConditionService currentConditionService;

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.GET)
    public ResponseEntity<?> getPatientCurrentCondition(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(currentConditionService.getPatientCondition(id), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.POST)
    public ResponseEntity<?> savePatientCondition(@PathVariable(value = "id") Long id, @RequestBody CurrentConditionDTO currentConditionDTO) {
        try {
            return new ResponseEntity<>(currentConditionService.savePatientCondition(id, currentConditionDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.CURRENT_CONDITION, method = RequestMethod.PUT)
    public ResponseEntity<?> updateCurrentCondition(@PathVariable(value = "id") Long id, @RequestBody CurrentConditionDTO currentConditionDTO) {
        try {
            return new ResponseEntity<>(currentConditionService.updatePatientCondition(id, currentConditionDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
