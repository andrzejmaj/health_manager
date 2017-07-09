package engineer.thesis.controller;

import engineer.thesis.exception.AlreadyExistsException;
import engineer.thesis.model.Patient;
import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.dto.ErrorDTO;
import engineer.thesis.model.dto.PatientDTO;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.repository.PersonalDetailsRepository;
import engineer.thesis.service.IPatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    protected IPatientService patientService;

    @RequestMapping(path = "/patients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatients() {
        System.out.println("PatientController - getAllPatients");
        List<PatientDTO> patients = patientService.getAllPatients();
        return new ResponseEntity<Object>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = "/patients", method = RequestMethod.POST)
    public ResponseEntity<?> savePatient(@RequestBody PatientDTO patientDTO) {
        try {
            return new ResponseEntity<>(patientService.savePatient(patientDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
// TODO: 09.07.17 wymaga innej implementajci w service
//    @RequestMapping(path = "/patients", method = RequestMethod.PUT)
//    public ResponseEntity<?> updatePatient(@RequestBody PatientDTO patientDTO) {
//        System.out.println(patientDTO);
//        try {
//            return new ResponseEntity<>(patientService.updatePatient(patientDTO), HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
//        }
//    }

    @RequestMapping(path = "/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatient(@PathVariable(value = "id") Long id) {
        try {

            return new ResponseEntity<>(patientService.findById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/patients/{id}/emergency", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientEmergency(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(patientService.findByIdEmergency(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/patients/pesel/{pesel}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientByPesel(@PathVariable("pesel") String pesel) {
        try {
            return new ResponseEntity<Object>(patientService.findByPesel(pesel), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Patient not fou nd", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/patients/searchByLastName", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientsByLastName(@RequestParam(value = "lastName", required = false) String lastName) {
        List<PatientDTO> patients = patientService.findPatientsByLastName(lastName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    /////////
    //DO TESTUF
//    ////////
//
//    @Autowired
//    PersonalDetailsRepository pdr;
//
//    @RequestMapping(path = "/test", method = RequestMethod.POST)
//    public ResponseEntity<?> getPatientsByLastName(@RequestBody PersonalDetailDTO pddto) {
//        System.out.println(pddto);
//        ModelMapper m = new ModelMapper();
//        return new ResponseEntity<>(pdr.save(m.map(pddto, PersonalDetails.class)), HttpStatus.OK);
//    }



}
