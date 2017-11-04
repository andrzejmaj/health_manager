package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Doctor;
import engineer.thesis.core.model.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor save(Doctor doctor);

    List<Doctor> findBySpecialization(Specialization specialization);

    Doctor findByAccount_User_Email(String email); // У тебе моя машина
}
