package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldDefaultValueDTO {
    @NotNull
    private Long formFieldId;
    @NotNull
    private String value;
}
