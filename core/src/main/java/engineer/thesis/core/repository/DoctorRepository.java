package engineer.thesis.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.Specialization;

import javax.print.Doc;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Doctor save(Doctor doctor);

	List<Doctor> findBySpecializations(Specialization specialization);
}
