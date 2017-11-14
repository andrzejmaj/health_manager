package engineer.thesis.core.repository.medcom;

import engineer.thesis.core.model.entity.medcom.Study;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public interface StudyRepository extends JpaRepository<Study, String> {

}
