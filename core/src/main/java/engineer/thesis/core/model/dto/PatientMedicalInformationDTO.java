package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.Patient;
import lombok.Value;

@Value
public class PatientMedicalInformationDTO {

    public Long patientId;
    public String allergies;
    public Integer weight;
    public Integer height;
    public String otherNotes;

    public PatientMedicalInformationDTO(Patient patient) {
        this.patientId = patient.getId();
        this.allergies = patient.getMedicalInformation().getAllergies();
        this.weight = patient.getMedicalInformation().getWeight();
        this.height = patient.getMedicalInformation().getHeight();
        this.otherNotes = patient.getMedicalInformation().getOtherNotes();
    }

}
