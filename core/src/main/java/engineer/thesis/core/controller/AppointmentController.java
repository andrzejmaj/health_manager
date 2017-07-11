package engineer.thesis.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.IAppointmentService;

@RestController
public class AppointmentController {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private IAppointmentService appointmentService;

	@RequestMapping(method = RequestMethod.GET, path = "/patients/{patientId}/appointments")
	public ResponseEntity<?> getAppointments(@PathVariable(name = "patientId") long patientId) {
		if (!patientRepository.exists(patientId)) {
			return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(appointmentRepository.findByPatientId(patientId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/patients/{patientId}/appointments/")
	public ResponseEntity<?> createAppointment(@PathVariable(name = "patientId") long patientId,
			AppointmentDTO appointmentDTO) {
		if (!patientRepository.exists(patientId)) {
			return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
		}

		if (appointmentService.exists(appointmentDTO)) {
			return new ResponseEntity<>("This appointment already exists", HttpStatus.BAD_REQUEST);
		}

		appointmentService.save(appointmentDTO, patientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/patients/{patientId}/appointments")
	public ResponseEntity<?> updateAppointment(@PathVariable(name = "patientId") long patientId,
			AppointmentDTO appointmentDTO) {
		if (!patientRepository.exists(patientId)) {
			return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
		}

		if (!appointmentService.exists(appointmentDTO)) {
			return new ResponseEntity<>("This appointment does not exist", HttpStatus.BAD_REQUEST);
		}

		appointmentService.save(appointmentDTO, patientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
