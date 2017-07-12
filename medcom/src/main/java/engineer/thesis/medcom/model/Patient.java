package engineer.thesis.medcom.model;

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
//TODO join with db patient
@Data
@Builder
public class Patient {

    private String patientId;

    @JsonInclude(NON_NULL)
    @Singular()
    private List<StudyInstance> studies;

}
