package engineer.thesis.core.repository;

import engineer.thesis.core.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    List<Drug> findByNameContainingIgnoreCase(String name);

}
