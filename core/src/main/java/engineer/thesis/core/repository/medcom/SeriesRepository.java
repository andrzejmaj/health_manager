package engineer.thesis.core.repository.medcom;

import engineer.thesis.core.model.entity.medcom.Series;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public interface SeriesRepository extends JpaRepository<Series, String> {

}
