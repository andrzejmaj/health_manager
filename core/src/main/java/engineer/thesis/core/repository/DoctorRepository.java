package engineer.thesis.core.repository;

import engineer.thesis.core.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialisation(String specialisation);
}
