package engineer.thesis.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MedcomPatientDTO extends PatientDTO2 {

    private int studyCount;
    private Date lastStudyDate;

    public MedcomPatientDTO(PatientDTO2 patient, int studyCount, Date lastStudyDate) {
        super(patient.getId(), patient.getAccount(), patient.getInsuranceNumber());
        this.studyCount = studyCount;
        this.lastStudyDate = lastStudyDate;
    }
}
