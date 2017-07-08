package engineer.thesis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private Long id;
    private AccountDTO account;
    private PersonalDetailDTO emergencyContact;
    private String insuranceNumber;
}
