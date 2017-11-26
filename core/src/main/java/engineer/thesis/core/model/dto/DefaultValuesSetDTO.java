package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultValuesSetDTO {
    private Long Id;
    @NotNull
    private String name;
    private Long formId;
    @NotNull
    @NotEmpty
    @Valid
    private List<FormFieldDefaultValueDTO> defaultValues;

}
