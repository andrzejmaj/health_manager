package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.MedicalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalInfoRepository extends JpaRepository<MedicalInformation, Long> {

}
