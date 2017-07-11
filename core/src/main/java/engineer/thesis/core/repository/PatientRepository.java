package engineer.thesis.repository;

import engineer.thesis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByAccount_PersonalDetails_Pesel(String pesel);

    List<Patient> findByAccount_PersonalDetails_LastNameLike(String lastName);

}