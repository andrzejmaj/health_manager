package engineer.thesis.core.repository;

import engineer.thesis.core.model.CurrentCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentConditionRepository extends JpaRepository<CurrentCondition, Long> {

    List<CurrentCondition> findByPatientId(Long id);

}
