package engineer.thesis.core.repository;

import engineer.thesis.core.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByPersonalDetails_Pesel(String pesel);

    List<Patient> findByPersonalDetails_LastNameLike(String lastName);

}