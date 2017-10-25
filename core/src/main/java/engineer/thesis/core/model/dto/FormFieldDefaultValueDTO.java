package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldDefaultValueDTO {
    private Long formFieldId;
    private String value;
}
