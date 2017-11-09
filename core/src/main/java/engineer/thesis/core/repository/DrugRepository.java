package engineer.thesis.core.repository;

import engineer.thesis.core.model.Drug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    Page<Drug> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Drug> findByNameContainingIgnoreCase(String name);

    Drug findByName(String name);
}
