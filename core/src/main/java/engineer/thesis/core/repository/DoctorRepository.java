package engineer.thesis.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.core.model.entity.Doctor;
import engineer.thesis.core.model.entity.Specialization;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Doctor save(Doctor doctor);

    List<Doctor> findBySpecialization(Specialization specialization);
}
