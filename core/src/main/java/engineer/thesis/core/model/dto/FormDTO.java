package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormDTO {
    private Long id;
    @NotNull
    private String name;
    @Valid
    @NotNull
    @NotEmpty
    private List<FormFieldDTO> formFields;
}
