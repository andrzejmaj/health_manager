package engineer.thesis.core.service.Interface;

import java.util.List;

import org.springframework.stereotype.Service;

import engineer.thesis.core.model.Appointment;
import engineer.thesis.core.model.dto.AppointmentDTO;

@Service
public interface IAppointmentService {

	void save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);

	AppointmentDTO findByTimeSlotId(long timeSlotId);

	List<AppointmentDTO> findByPatientId(long patientId);
}
