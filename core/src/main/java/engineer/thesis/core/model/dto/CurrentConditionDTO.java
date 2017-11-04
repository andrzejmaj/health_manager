package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrentConditionDTO {

    private Long Id;
    @NotNull
    private Long patientId;
    @NotNull
    private String diseaseName;
    @NotNull
    private String symptoms;
    @NotNull
    private List<DrugDTO> takenDrugs;

}
