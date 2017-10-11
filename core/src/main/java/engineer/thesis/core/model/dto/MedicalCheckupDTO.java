package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalCheckupDTO {

    private Long id;
    private List<MedicalCheckupValueDTO> medicalCheckupValues;
    private PatientDTO2 patient;
    private UserDTO creator;
    private Date createdDate;
    private Date lastModifiedDate;
}
