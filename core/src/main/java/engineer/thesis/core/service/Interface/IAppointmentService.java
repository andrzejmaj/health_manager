package engineer.thesis.core.service.Interface;

import org.springframework.stereotype.Service;

import engineer.thesis.core.model.dto.AppointmentDTO;

@Service
public interface IAppointmentService {

	AppointmentDTO save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);

	AppointmentDTO find(long appointmentId);
}
