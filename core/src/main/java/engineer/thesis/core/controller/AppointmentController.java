package engineer.thesis.core.controller;

import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.service.Interface.IAppointmentService;
import engineer.thesis.core.service.Interface.IPatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppointmentController {

    @Autowired
	private IPatientService patientService;
    @Autowired
    private IAppointmentService appointmentService;


    @RequestMapping(method = RequestMethod.GET, path = "/appointments/byTimeSlot/{timeSlotId}")
    public ResponseEntity<?> getAppointmentByTimeSlot(@PathVariable(name = "timeSlotId") long timeSlotId) {
		AppointmentDTO appointmentDTO = appointmentService.findByTimeSlotId(timeSlotId);
		if (appointmentDTO != null) {
			return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No appointment with timeSlotId " + timeSlotId, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/patients/{patientId}/appointments")
    public ResponseEntity<?> getAppointments(@PathVariable(name = "patientId") long patientId) {
		if (!patientService.exists(patientId)) {
            return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
        }

		return new ResponseEntity<>(appointmentService.findByPatientId(patientId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/patients/{patientId}/appointments")
    public ResponseEntity<?> createAppointment(@PathVariable(name = "patientId") long patientId,
                                               @RequestBody AppointmentDTO appointmentDTO) {
		if (!patientService.exists(patientId)) {
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
		if (!patientService.exists(patientId)) {
            return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
        }

        if (!appointmentService.exists(appointmentDTO)) {
            return new ResponseEntity<>("This appointment does not exist", HttpStatus.BAD_REQUEST);
        }

        appointmentService.save(appointmentDTO, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
