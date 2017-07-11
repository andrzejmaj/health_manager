package engineer.thesis.service;

import org.springframework.stereotype.Service;

import engineer.thesis.model.Appointment;
import engineer.thesis.model.dto.AppointmentDTO;

@Service
public interface IAppointmentService {

	void save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);
}
