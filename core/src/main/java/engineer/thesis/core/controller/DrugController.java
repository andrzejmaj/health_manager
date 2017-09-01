package engineer.thesis.core.controller;

import engineer.thesis.core.service.Interface.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}