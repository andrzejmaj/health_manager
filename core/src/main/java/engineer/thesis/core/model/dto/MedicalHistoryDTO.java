package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalHistoryDTO {

    private Long id;
    @NotNull
    private String symptoms;
    @NotNull
    private Long patientId;
    @NotNull
    private String diseaseName;
    @NotNull
    private Date detectionDate;
    private Date cureDate;

}
