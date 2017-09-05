package engineer.thesis.core.repository;

import engineer.thesis.core.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    List<Form> findByName(String name);

    List<Form> findByOwner_Id(Long id);

}