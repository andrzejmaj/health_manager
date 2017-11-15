package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
class MedicalCheckupValueDTO {
    @NotNull
    private String value;
    @NotNull
    private Long formFieldId;
}
