package engineer.thesis.core.model.dto;


import engineer.thesis.core.model.MedicalHistory;
import lombok.Value;

import java.util.Date;

@Value
public class MedicalHistoryDTO {

    private final Long id;
    private final String name;
    private final String symptoms;
    private final Long patientId;
    private final Long diseaseId;
    private final String diseaseName;
    private final Date detectionDate;
    private final Date cureDate;

    public MedicalHistoryDTO(MedicalHistory medicalHistory) {
        this.id = medicalHistory.getId();
        this.name = medicalHistory.getName();
        this.symptoms = medicalHistory.getSymptoms();
        this.patientId = medicalHistory.getPatient().getId();
        this.diseaseId = medicalHistory.getDisease().getId();
        this.diseaseName = medicalHistory.getDisease().getName();
        this.detectionDate = medicalHistory.getDetectionDate();
        this.cureDate = medicalHistory.getCureDate();
    }
}
