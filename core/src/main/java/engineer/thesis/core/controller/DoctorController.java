package engineer.thesis.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import engineer.thesis.core.model.dto.DoctorDTO;
import engineer.thesis.core.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.TimeSlotRepository;

@RestController
public class DoctorController {

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Autowired
	private DoctorService doctorService;

	@RequestMapping(method = RequestMethod.GET, path = "/doctors")
	public ResponseEntity<?> getDoctors() {
		return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/doctors/{id}")
	public ResponseEntity<?> getDoctor(@PathVariable(name = "id") long id) {
        try {
             return new ResponseEntity<>(doctorService.findByID(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
        }
	}

	@RequestMapping(method = RequestMethod.GET, path = "/doctors/{docId}/slots")
	public ResponseEntity<?> getTimeSlots(@PathVariable(name = "docId") long docId,
			@RequestParam(name = "type", defaultValue = "all") String slotType) {
		Doctor doc = doctorRepository.findOne(docId);
		if (doc == null) {
			return new ResponseEntity<>("No doctor with id: " + docId, HttpStatus.NOT_FOUND);
		}

		List<TimeSlot> slots;
		if (slotType.equals("available")) {
			slots = timeSlotRepository.findAvailableByDoctor(doc);
		} else if (slotType.equals("taken")) {
			slots = timeSlotRepository.findTakenByDoctor(doc);
		} else {
			slots = timeSlotRepository.findByDoctor(doc);
		}

		return new ResponseEntity<>(slots, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/doctors/{docId}/slots/{slotId}")
	public ResponseEntity<?> getTimeSlot(@PathVariable(name = "docId") long docId,
			@PathVariable(name = "slotId") long slotId) {
		if (!doctorRepository.exists(docId)) {
			return new ResponseEntity<>("No doctor with id: " + docId, HttpStatus.NOT_FOUND);
		}

		TimeSlot slot = timeSlotRepository.findOne(slotId);
		if (slot == null) {
			return new ResponseEntity<>("No time slot with id: " + slotId, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(slot, HttpStatus.OK);
	}
}
