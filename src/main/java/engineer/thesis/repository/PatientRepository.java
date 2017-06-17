package engineer.thesis.repository;

import engineer.thesis.model.MedicalHistory;
import engineer.thesis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByPersonalDetails_Pesel(String pesel);

    List<Patient> findByPersonalDetails_LastNameLike(String lastName);

}