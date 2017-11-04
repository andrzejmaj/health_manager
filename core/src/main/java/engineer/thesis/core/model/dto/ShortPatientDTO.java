package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortPatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
}
