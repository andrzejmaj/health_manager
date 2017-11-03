package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalCheckupValueDTO {
    private Long id;
    private String value;
    private Long fieldId;
}
