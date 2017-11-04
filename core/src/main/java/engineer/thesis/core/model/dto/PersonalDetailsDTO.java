package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalDetailsDTO {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String gender;
    @NotNull
    private String pesel;
    @NotNull
    private Date birthdate;
    private String phoneNumber;
    @NotNull
    private String country;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private Integer buildingNumber;
    private Integer flatNumber;
}
