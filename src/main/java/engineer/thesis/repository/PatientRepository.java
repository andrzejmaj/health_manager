package engineer.thesis.repository;

import engineer.thesis.model.MedicalHistory;
import engineer.thesis.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    Patient findByUser_PersonalDetails_Pesel(String pesel);

    List<Patient> findByUser_PersonalDetails_LastNameLike(String lastName);

}