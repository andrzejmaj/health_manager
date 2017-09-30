package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormDTO {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private List<FormFieldDTO> formFields;
    @NotNull
    private UserDTO owner;

}
