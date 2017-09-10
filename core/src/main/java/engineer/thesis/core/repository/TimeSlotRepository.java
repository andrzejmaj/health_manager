package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Doctor;
import engineer.thesis.core.model.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

	List<TimeSlot> findByDoctor(Doctor doctor);

	@Query("select t from Appointment a right join a.timeSlot t where t.doctor = :doctor and a is null")
	List<TimeSlot> findAvailableByDoctor(@Param("doctor") Doctor doctor);

	@Query("select t from Appointment a right join a.timeSlot t where t.doctor = :doctor and a is not null")
	List<TimeSlot> findTakenByDoctor(@Param("doctor") Doctor doctor);

	@Query("from TimeSlot t where t.doctor = :doctor and ((t.startDateTime <= :startDateTime and :startDateTime < t.endDateTime) or (t.startDateTime < :endDateTime and :endDateTime <= t.endDateTime))")
	List<TimeSlot> findInterleaving(@Param("doctor") Doctor doctor, @Param("startDateTime") Date startDateTime,
			@Param("endDateTime") Date endDateTime);

	@Query("from TimeSlot t where t.doctor.id = :doctorId and (t.startDateTime >= :startDateTime and t.endDateTime <= :endDateTime)")
	List<TimeSlot> findInInterval(@Param("doctorId") long doctorId, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

}
