package engineer.thesis.model.dto;

import engineer.thesis.model.MedicalInformation;
import lombok.Value;

@Value
public class PatientMedicalInformationDTO {

    public Long patientId;
    public String allergies;
    public Integer weight;
    public Integer height;
    public String otherNotes;

    public PatientMedicalInformationDTO(MedicalInformation information) {
        this.patientId = information.getPatient().getId();
        this.allergies = information.getAllergies();
        this.weight = information.getWeight();
        this.height = information.getHeight();
        this.otherNotes = information.getOtherNotes();
    }

}
