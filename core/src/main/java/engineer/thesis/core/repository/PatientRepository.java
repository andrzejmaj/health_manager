package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByAccount_PersonalDetails_Pesel(String pesel);

    List<Patient> findByAccount_PersonalDetails_LastNameIgnoreCase(String lastName);

    Patient findByAccount_User_Email(String email); //magic :д
}