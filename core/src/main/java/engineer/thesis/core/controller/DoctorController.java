package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.dto.DoctorDTO;
import engineer.thesis.core.repository.DoctorRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.IDoctorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorController {

    private final static Logger logger = Logger.getLogger(DoctorController.class);

    @Autowired
    protected IDoctorService doctorService;

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@RequestMapping(method = RequestMethod.GET, path = "/doctors")
	public ResponseEntity<?> getDoctors() {
		List<Doctor> list = new ArrayList<>();

		doctorRepository.findAll().forEach(list::add);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

    @RequestMapping(path = "/doctors", method = RequestMethod.POST)
    public ResponseEntity<?> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
        try {
            return new ResponseEntity<>(doctorService.saveDoctor(doctorDTO), HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

	@RequestMapping(method = RequestMethod.GET, path = "/doctors/{id}")
	public ResponseEntity<?> getDoctor(@PathVariable(name = "id") long id) {
		Doctor doc = doctorRepository.findOne(id);
		if (doc == null) {
			return new ResponseEntity<>("No doctor with id: " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(doc, HttpStatus.OK);
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
