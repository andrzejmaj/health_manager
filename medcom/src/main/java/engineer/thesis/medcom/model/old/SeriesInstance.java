package engineer.thesis.medcom.model.old;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@Data
@Builder
public class SeriesInstance {

    private String seriesInstanceUid;

    @JsonInclude(NON_NULL)
    private String studyInstanceUid;

    @JsonInclude(NON_NULL)
    private String patientId;

    @JsonInclude(NON_NULL)
    @Singular()
    private List<DicomInstance> dicoms;
}
