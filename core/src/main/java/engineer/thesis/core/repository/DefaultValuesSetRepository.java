package engineer.thesis.core.repository;

import engineer.thesis.core.model.DefaultValuesSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultValuesSetRepository extends JpaRepository<DefaultValuesSet, Long> {
    List<DefaultValuesSet> findAllByForm_Id(Long id);
}
