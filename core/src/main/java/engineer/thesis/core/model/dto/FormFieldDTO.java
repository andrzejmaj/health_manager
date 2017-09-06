package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormFieldDTO {

    private Long id;
    private FormFieldTypeDTO fieldType;
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
