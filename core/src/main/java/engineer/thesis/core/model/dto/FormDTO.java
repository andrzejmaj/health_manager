package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FormDTO {

    private Long id;
    private String name;
    private List<FormFieldDTO> formFields;
    private UserDTO owner;

}
