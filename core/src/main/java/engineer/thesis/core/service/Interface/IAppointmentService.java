package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.dto.AppointmentDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAppointmentService {

	void save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);
}
