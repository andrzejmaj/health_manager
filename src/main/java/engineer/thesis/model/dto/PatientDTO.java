package engineer.thesis.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private Long id;
    private AccountDTO account;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PersonalDetailDTO emergencyContact;
    private String insuranceNumber;
}
