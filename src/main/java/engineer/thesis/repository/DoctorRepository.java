package engineer.thesis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.model.Doctor;
import engineer.thesis.model.Specialization;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecializations(Specialization specialization);
}
