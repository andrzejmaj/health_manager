package engineer.thesis.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.Specialization;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecializations(Specialization specialization);
}
