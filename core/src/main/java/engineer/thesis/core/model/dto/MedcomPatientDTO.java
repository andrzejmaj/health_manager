package engineer.thesis.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
public class MedcomPatientDTO extends PatientDTO {

    public MedcomPatientDTO(PatientDTO patient, int studyCount, Date lastStudyDate) {
        super(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGender(),
                patient.getPesel(),
                patient.getBirthdate(),
                patient.getPhoneNumber(),
                patient.getCountry(),
                patient.getStreet(),
                patient.getCity(),
                patient.getBuildingNumber(),
                patient.getFlatNumber(),
                patient.getInsuranceNumber()
        );
        this.studyCount = studyCount;
        this.lastStudyDate = lastStudyDate;
    }

    private int studyCount;
    private Date lastStudyDate;
}
