package engineer.thesis.core.repository;

import engineer.thesis.core.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    List<MedicalHistory> findAllByPatient_Id(Long id);

    List<MedicalHistory> findAllByPatient_IdAndDetectionDateBetween(Long id, Date start, Date end);
}
