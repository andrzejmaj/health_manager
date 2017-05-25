package engineer.thesis.repository;

import engineer.thesis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByPersonalDetails_Pesel(String pesel);

}
