package engineer.thesis.model.dto;

import engineer.thesis.model.Patient;
import lombok.Value;

@Value
public class PatientDTO {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String gender;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getPersonalDetails().getFirstName();
        this.lastName = patient.getPersonalDetails().getLastName();
        this.gender = patient.getPersonalDetails().getGender();
    }
}
