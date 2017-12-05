package engineer.thesis.core.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
class MedicalHistoryDTO {
    private Long id;
    private Long patientId;
    @NotNull
    private String diseaseName;
    private String symptoms;
    @NotNull
    private Date detectionDate;
}