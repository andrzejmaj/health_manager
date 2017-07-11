package engineer.thesis.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.core.model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

}
