package engineer.thesis.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

	List<TimeSlot> findByDoctor(Doctor doctor);

	@Query("select t from Appointment a right join a.timeSlot t where t.doctor = :doctor and a is null")
	List<TimeSlot> findAvailableByDoctor(@Param("doctor") Doctor doctor);

	@Query("select t from Appointment a right join a.timeSlot t where t.doctor = :doctor and a is not null")
	List<TimeSlot> findTakenByDoctor(@Param("doctor") Doctor doctor);
}
