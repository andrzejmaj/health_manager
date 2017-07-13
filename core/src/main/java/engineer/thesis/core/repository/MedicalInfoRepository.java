package engineer.thesis.core.repository;

import engineer.thesis.core.model.MedicalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInfoRepository extends JpaRepository<MedicalInformation, Long> {

}
