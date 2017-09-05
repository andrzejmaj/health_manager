package engineer.thesis.core.repository;

import engineer.thesis.core.model.MedicalCheckup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalCheckupRepository extends JpaRepository<MedicalCheckup, Long> {

    List<MedicalCheckup> findAllByPatientId_Id(Long id);
}
