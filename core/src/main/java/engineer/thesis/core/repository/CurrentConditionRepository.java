package engineer.thesis.core.repository;

import engineer.thesis.core.model.PatientCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentConditionRepository extends JpaRepository<PatientCondition, Long> {

    List<PatientCondition> findByPatientId(Long id);

}
