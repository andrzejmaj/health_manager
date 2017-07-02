package engineer.thesis.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String pesel;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
    private Date birthdate;
    private String phoneNumber;
    private String country;
    private String street;
    private String city;
    private Integer buildingNumber;
    private Integer flatNumber;
}
