package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalInfoDTO {

    private Long id;
    @NotNull
    private String allergies;
    @NotNull
    private Integer weight;
    @NotNull
    private Integer height;
    private String otherNotes;

}
