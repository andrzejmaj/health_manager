package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.MedicalCheckup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalCheckupRepository extends JpaRepository<MedicalCheckup, Long> {

    List<MedicalCheckup> findAllByPatientIdOrderByLastModifiedDateDesc(Long id);
}
