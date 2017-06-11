package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.Patient;
import lombok.Value;

import java.util.Date;

@Value
public class PatientDTO {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String pesel;
    private final Date birthdate;
    private final String phoneNumber;
    private final String country;
    private final String street;
    private final String city;
    private final Integer buildingNumber;
    private final Integer flatNumber;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getPersonalDetails().getFirstName();
        this.lastName = patient.getPersonalDetails().getLastName();
        this.gender = patient.getPersonalDetails().getGender();
        this.pesel = patient.getPersonalDetails().getPesel();
        this.birthdate = patient.getPersonalDetails().getBirthDate();
        this.phoneNumber = patient.getPersonalDetails().getPhoneNumber();
        this.country = patient.getPersonalDetails().getCountry();
        this.city = patient.getPersonalDetails().getCity();
        this.street = patient.getPersonalDetails().getStreet();
        this.buildingNumber = patient.getPersonalDetails().getBuildingNumber();
        this.flatNumber = patient.getPersonalDetails().getFlatNumber();
    }
}
