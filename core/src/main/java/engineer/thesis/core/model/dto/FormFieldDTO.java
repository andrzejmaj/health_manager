package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormFieldDTO {

    private Long id;
    @NotNull
    private String type;
    @NotNull
    private String name;
    private Boolean isRequired;
    private Boolean isEditable;
    private String label;
    private String placeholder;
    private String contextualText;
    private String warningText;
    private String errorText;
    private List<FormAvailableValueDTO> fieldAvailableValues;

}
