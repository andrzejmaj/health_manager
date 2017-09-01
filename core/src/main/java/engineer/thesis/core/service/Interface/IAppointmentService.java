package engineer.thesis.core.service.Interface;

import org.springframework.stereotype.Service;

import engineer.thesis.core.model.dto.AppointmentDTO;

@Service
public interface IAppointmentService {

	void save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);
}
