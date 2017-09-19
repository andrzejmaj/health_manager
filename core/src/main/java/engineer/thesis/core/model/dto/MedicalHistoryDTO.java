package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalHistoryDTO {

    private Long id;
    private String name;
    private String symptoms;
    private Long patientId;
    private String diseaseName;
    private Date detectionDate;
    private Date cureDate;

}
