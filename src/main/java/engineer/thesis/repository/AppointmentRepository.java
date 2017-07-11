package engineer.thesis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import engineer.thesis.model.Appointment;
import engineer.thesis.model.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	public List<Appointment> findByPatient(Patient patient);

	@Query("from Appointment a where a.patient.id = :patientId")
	public List<Appointment> findByPatientId(@Param("patientId") long patientId);

	@Query("from Appointment a where a.timeSlot.id = :timeSlotId")
	public Appointment findByTimeSlotId(@Param("timeSlotId") long timeSlotId);
}
