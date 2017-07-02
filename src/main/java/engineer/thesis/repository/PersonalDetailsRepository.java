package engineer.thesis.repository;

import engineer.thesis.model.PersonalDetails;
import engineer.thesis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {


}
