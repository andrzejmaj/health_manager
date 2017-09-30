package engineer.thesis.core.repository.medcom;

import engineer.thesis.core.model.entity.medcom.Modality;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public interface ModalityRepository extends JpaRepository<Modality, String> {

}
