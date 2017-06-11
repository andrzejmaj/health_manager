package engineer.thesis.medcom.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
/* TODO
 * maybe separate medcom model will not be needed
 * and database models/dtos from engineer.thesis.core.model will be used instead
**/
@Data
@Builder
public class DicomInstance {

    private String sopInstanceUid;

    @JsonInclude(NON_NULL)
    private String seriesInstanceUid;

    @JsonInclude(NON_NULL)
    private String studyInstanceUid;

    @JsonInclude(NON_NULL)
    private String patientId;

}
