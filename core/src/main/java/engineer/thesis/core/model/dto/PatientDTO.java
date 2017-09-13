package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private Long id;
    private AccountDTO account;
    private String insuranceNumber;
    private Date lastDicomStudyDate;
}
