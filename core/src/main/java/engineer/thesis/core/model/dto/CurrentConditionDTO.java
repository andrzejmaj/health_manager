package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrentConditionDTO {

    private Long Id;
    private Long patientId;
    private String conditionName;
    private String symptoms;
    private List<DrugDTO> takenDrugs;

}
