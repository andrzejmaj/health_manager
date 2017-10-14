package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContactDTO {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String street;
    private String city;
    private Integer buildingNumber;
    private Integer flatNumber;
}
