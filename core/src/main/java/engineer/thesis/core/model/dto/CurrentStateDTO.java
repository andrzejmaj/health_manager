package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.CurrentDrug;
import lombok.Value;

import java.util.List;

@Value
public class CurrentStateDTO {

    private Long patientId;
    private String condition;
    private String symptoms;
    private List<DrugDTO> takenDrugs;

    public CurrentStateDTO(CurrentDrug drug, List<DrugDTO> takenDrugs) {
        this.patientId = drug.getPatient().getId();
        this.condition = drug.getCondition().getCondition();
        this.symptoms = drug.getCondition().getSymptoms();
        this.takenDrugs = takenDrugs;
    }

}
