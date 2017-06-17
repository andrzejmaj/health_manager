package engineer.thesis.repository;

import engineer.thesis.model.CurrentDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentDrugRepository extends JpaRepository<CurrentDrug, Long> {

    List<CurrentDrug> findByPatientId(Long id);

}
