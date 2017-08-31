package engineer.thesis.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.core.model.Doctor;
import engineer.thesis.core.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	Doctor save(Doctor doctor);

    List<Doctor> findBySpecialization(Specialization specialization);
}
