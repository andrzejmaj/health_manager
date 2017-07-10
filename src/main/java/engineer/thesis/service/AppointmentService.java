package engineer.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.thesis.model.Appointment;
import engineer.thesis.model.Patient;
import engineer.thesis.model.TimeSlot;
import engineer.thesis.model.dto.AppointmentDTO;
import engineer.thesis.repository.AppointmentRepository;
import engineer.thesis.repository.PatientRepository;
import engineer.thesis.repository.TimeSlotRepository;

@Service
public class AppointmentService implements IAppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Override
	public AppointmentDTO mapToDTO(Appointment data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Appointment mapFromDTO(AppointmentDTO dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void save(AppointmentDTO appointmentDTO, long patientId) {
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

}
