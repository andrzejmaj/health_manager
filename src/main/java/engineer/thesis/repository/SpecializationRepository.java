package engineer.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import engineer.thesis.model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

}
