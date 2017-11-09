package engineer.thesis.core.repository;

import engineer.thesis.core.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findAllByAppointment_Patient_Id(Long id);
}
