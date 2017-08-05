package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.CurrentDrug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrentConditionDTO {

    private Long patientId;
    private String condition;
    private String symptoms;
    private List<DrugDTO> takenDrugs;

    public CurrentConditionDTO(CurrentDrug drug, List<DrugDTO> takenDrugs) {
        this.patientId = drug.getPatient().getId();
        this.condition = drug.getCondition().getCondition();
        this.symptoms = drug.getCondition().getSymptoms();
        this.takenDrugs = takenDrugs;
    }

}
