package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMedicalHistoryDTO extends MedicalHistoryDTO {
    private ResponseMedicalCheckupDTO medicalCheckup;
}
