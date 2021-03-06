package engineer.thesis.core.controller;

import engineer.thesis.core.model.entity.Specialization;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.SpecializationRepository;
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
    private SpecializationRepository specializationRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/specializations")
    public ResponseEntity<?> getSpecializations() {
        List<Specialization> list = new ArrayList<>();

        specializationRepository.findAll().forEach(list::add);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/specializations/{id}")
    public ResponseEntity<?> getSpecialization(@PathVariable(name = "id") long id) {
        Specialization spec = specializationRepository.findOne(id);
        if (spec == null) {
            return new ResponseEntity<>("No specialization with id: " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(spec, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/specializations/{specId}/doctors")
    public ResponseEntity<?> getDoctorsBySpecializaion(@PathVariable(name = "specId") long specId) {
        Specialization spec = specializationRepository.findOne(specId);
        if (spec == null) {
            return new ResponseEntity<>("No specialization with id: " + specId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorRepository.findBySpecialization(spec), HttpStatus.OK);
    }
}
