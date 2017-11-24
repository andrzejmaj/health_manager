package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.entity.MedicalCheckupValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ResponseMedicalCheckupValueDTO extends MedicalCheckupValueDTO {
    private String name;

    public ResponseMedicalCheckupValueDTO(MedicalCheckupValue val, String label) {
        super(val.getValue(), val.getFormFieldId());
        this.name = label;
    }
}
