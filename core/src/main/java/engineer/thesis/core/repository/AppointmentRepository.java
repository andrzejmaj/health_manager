package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Appointment;
import engineer.thesis.core.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	public List<Appointment> findByPatient(Patient patient);

	@Query("from Appointment a where a.patient.id = :patientId")
	public List<Appointment> findByPatientId(@Param("patientId") long patientId);

	@Query("from Appointment a where a.timeSlot.id = :timeSlotId")
	public Appointment findByTimeSlotId(@Param("timeSlotId") long timeSlotId);

	@Query("select a from Appointment a where a.timeSlot.doctor.id = :doctorId and (a.timeSlot.endDateTime >= :startDateTime and a.timeSlot.startDateTime <= :endDateTime)")
	List<Appointment> findInIntervalForDoctor(@Param("doctorId") long doctorId, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

	@Query("select a from Appointment a where a.patient.id = :patientId and (a.timeSlot.endDateTime >= :startDateTime and a.timeSlot.startDateTime <= :endDateTime)")
	List<Appointment> findInIntervalForPatient(@Param("patientId") long doctorId, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);
}
