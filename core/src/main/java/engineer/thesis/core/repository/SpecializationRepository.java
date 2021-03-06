package engineer.thesis.core.repository;


import engineer.thesis.core.model.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    public Specialization findByDescription(String description);
}
