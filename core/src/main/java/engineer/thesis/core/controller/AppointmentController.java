package engineer.thesis.core.controller;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.entity.Appointment;
import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.service.Interface.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private IAppointmentService appointmentService;


    @RequestMapping(method = RequestMethod.GET, path = "/appointments/byTimeSlot/{timeSlotId}")
    public ResponseEntity<?> getAppointmentByTimeSlot(@PathVariable(name = "timeSlotId") long timeSlotId) {
        Appointment appointment = appointmentRepository.findByTimeSlotId(timeSlotId);
        if (appointment != null) {
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No appointment with timeSlotId " + timeSlotId, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/appointments/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) {
        try {
            return new ResponseEntity<>(appointmentService.getById(id), HttpStatus.OK);
        } catch (NoSuchElementExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/patients/{patientId}/appointments")
    public ResponseEntity<?> getAppointments(@PathVariable(name = "patientId") long patientId) {
        if (!patientRepository.exists(patientId)) {
            return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(appointmentRepository.findByPatientId(patientId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/appointments/{appointmentId}")
    public ResponseEntity<?> removeAppointment(@PathVariable(name = "appointmentId") long appointmentId) {
        AppointmentDTO appointmentDTO = appointmentService.find(appointmentId);
        if (appointmentDTO == null) {
            return new ResponseEntity<>("This appointment does not exists", HttpStatus.BAD_REQUEST);
        }
        appointmentRepository.delete(appointmentId);
        return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/patients/{patientId}/appointments")
    public ResponseEntity<?> createAppointment(@PathVariable(name = "patientId") long patientId,
                                               @RequestBody AppointmentDTO appointmentDTO) {
        if (!patientRepository.exists(patientId)) {
            return new ResponseEntity<>("Patient with id " + patientId + "not found", HttpStatus.NOT_FOUND);
        }

        if (appointmentService.exists(appointmentDTO)) {
            return new ResponseEntity<>("This appointment already exists", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(appointmentService.save(appointmentDTO, patientId), HttpStatus.OK);
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

        return new ResponseEntity<>(appointmentService.save(appointmentDTO, patientId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/appointmentsForDoc/{doctorId}/{start}/{end}")
    public ResponseEntity<?> getForDoctor(
            @PathVariable(name = "doctorId") long doctorId,
            @PathVariable(name = "start") long start,
            @PathVariable(name = "end") long end) {
        return new ResponseEntity<>(appointmentService.getInIntervalForDoctor(doctorId, new Date(start), new Date(end)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/appointmentsForPatient/{patientId}/{start}/{end}")
    public ResponseEntity<?> getForPatient(
            @PathVariable(name = "patientId") long patientId,
            @PathVariable(name = "start") long start,
            @PathVariable(name = "end") long end) {
        return new ResponseEntity<>(appointmentService.getInIntervalForPatient(patientId, new Date(start), new Date(end)), HttpStatus.OK);
    }
}
