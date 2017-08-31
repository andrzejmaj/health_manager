package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalInfoDTO {

    private Long id;
    private String allergies;
    private Integer weight;
    private Integer height;
    private String otherNotes;

}
