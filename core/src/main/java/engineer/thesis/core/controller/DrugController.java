package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.service.Interface.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DrugController {

    @Autowired
    private IDrugService drugService;

    @RequestMapping(path = RequestMappings.DRUGS.DRUGS, method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrugs() {
        return new ResponseEntity<Object>(drugService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.DRUGS.DRUGS, method = RequestMethod.GET, params = "name")
    public ResponseEntity<?> getAllDrugsByName(@RequestParam String name) {
        return new ResponseEntity<Object>(drugService.findAllByName(name), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.DRUGS.DRUGS, method = RequestMethod.GET, params = {"page", "size", "name"})
    public ResponseEntity<?> getAllDrugsByNamePageable(Pageable pageable, @RequestParam String name) {
        return new ResponseEntity<Object>(drugService.findAllPageable(name, pageable), HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.DRUGS.DRUGS_ID, method = RequestMethod.GET)
    public ResponseEntity<?> getDrug(@PathVariable Long id) {
        try {
            return new ResponseEntity<Object>(drugService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}