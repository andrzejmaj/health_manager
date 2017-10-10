package engineer.thesis.core.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class MedcomPatientDTO extends PatientDTO {

    public MedcomPatientDTO(PatientDTO patient, int studyCount, Date lastStudyDate) {
        super(patient.getId(), patient.getAccount(), patient.getInsuranceNumber());
        this.studyCount = studyCount;
        this.lastStudyDate = lastStudyDate;
    }

    private int studyCount;
    private Date lastStudyDate;
}