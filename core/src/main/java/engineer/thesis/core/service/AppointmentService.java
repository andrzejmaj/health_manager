package engineer.thesis.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.thesis.core.model.Appointment;
import engineer.thesis.core.model.Patient;
import engineer.thesis.core.model.TimeSlot;
import engineer.thesis.core.model.dto.AppointmentDTO;
import engineer.thesis.core.repository.AppointmentRepository;
import engineer.thesis.core.repository.PatientRepository;
import engineer.thesis.core.repository.TimeSlotRepository;

@Service
public class AppointmentService implements IAppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;

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
