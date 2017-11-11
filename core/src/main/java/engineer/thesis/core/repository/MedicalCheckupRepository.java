package engineer.thesis.core.repository;

import engineer.thesis.core.model.MedicalCheckup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalCheckupRepository extends JpaRepository<MedicalCheckup, Long> {
}
