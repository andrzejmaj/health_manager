package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.NoSuchElementExistsException;
import engineer.thesis.core.model.dto.AppointmentDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IAppointmentService {

    AppointmentDTO getById(long id) throws NoSuchElementExistsException;

	AppointmentDTO save(AppointmentDTO appointmentDTO, long patientId);

	boolean exists(AppointmentDTO appointmentDTO);

	AppointmentDTO find(long appointmentId);

	List<AppointmentDTO> getInIntervalForDoctor(long doctorId, Date startDate, Date endDate) throws IllegalArgumentException;

	List<AppointmentDTO> getInIntervalForPatient(long patientId, Date startDate, Date endDate) throws IllegalArgumentException;
}
