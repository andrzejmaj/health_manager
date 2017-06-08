package engineer.thesis.repository;

import engineer.thesis.model.MedicalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInformationRepository extends JpaRepository<MedicalInformation, Long> {

    MedicalInformation findByPatientId(Long id);

}
