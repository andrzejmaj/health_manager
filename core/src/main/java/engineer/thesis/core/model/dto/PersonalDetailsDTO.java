package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String pesel;
    private Date birthdate;
    private String phoneNumber;
    private String country;
    private String street;
    private String city;
    private Integer buildingNumber;
    private Integer flatNumber;
}
