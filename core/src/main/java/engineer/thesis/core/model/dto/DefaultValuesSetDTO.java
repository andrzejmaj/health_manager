package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultValuesSetDTO {
    private Long Id;
    @NotNull
    private Long formId;
    private List<FormFieldDefaultValueDTO> defaultValues;

}
