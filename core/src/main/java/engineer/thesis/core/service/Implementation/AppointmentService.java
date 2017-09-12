package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.model.Appointment;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.TimeSlotRepository;
import engineer.thesis.core.service.Interface.IAppointmentService;
import engineer.thesis.core.utils.CustomObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService implements IAppointmentService {
    private final static Logger logger = Logger.getLogger(AppointmentService.class);

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@Autowired
	private CustomObjectMapper objectMapper;

	@Override
	public void save(AppointmentDTO appointmentDTO, long patientId) {
        logger.info("Saveing new appointment for " + patientId + ".\n" + appointmentDTO.toString());
        Appointment oldAppointment = appointmentRepository.findByTimeSlotId(appointmentDTO.getTimeSlotId());
        if (oldAppointment != null) {
			appointmentRepository.delete(oldAppointment);
		}
		
		Patient patient = patientRepository.findOne(patientId);
		if (patient == null) {
			throw new IllegalArgumentException("No patient with id " + patientId);
		}
		TimeSlot timeSlot = timeSlotRepository.findOne(appointmentDTO.getTimeSlotId());
		if (timeSlot == null) {
			throw new IllegalArgumentException("No timeSlot with id " + appointmentDTO.getTimeSlotId());
		}
		Integer officeNumber = appointmentDTO.getOfficeNumber();
		boolean tookPlace = appointmentDTO.getTookPlace();
		String data = appointmentDTO.getData();
		Appointment newAppointment = new Appointment(patient, timeSlot, officeNumber, tookPlace, data);

		appointmentRepository.save(newAppointment);
	}

	@Override
	public boolean exists(AppointmentDTO appointmentDTO) {
		return appointmentRepository.findByTimeSlotId(appointmentDTO.getTimeSlotId()) != null;
	}

	@Override
	public AppointmentDTO findByTimeSlotId(long timeSlotId) {
		return Optional.ofNullable(appointmentRepository.findByTimeSlotId(timeSlotId))
				.map(appointment -> objectMapper.convert(appointment, AppointmentDTO.class)).orElse(null);
	}

	@Override
	public List<AppointmentDTO> findByPatientId(long patientId) {
		return appointmentRepository.findByPatientId(patientId).stream()
				.map(appointment -> objectMapper.convert(appointment, AppointmentDTO.class))
				.collect(Collectors.toList());
	}

}
