package engineer.thesis.core.controller;

import engineer.thesis.core.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SpecializationController {

	@Autowired
	private DoctorRepository doctorRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/specializations/{specName}/doctors")
    public ResponseEntity<?> getDoctorsBySpecializaion(@PathVariable(name = "specName") String specName) {
        return new ResponseEntity<>(doctorRepository.findBySpecialisation(specName), HttpStatus.OK);
    }
}
