package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMedicalCheckupDTO {
    private Long id;
    private Long formId;
    private List<ResponseMedicalCheckupValueDTO> medicalCheckupValues;
    private Date createdDate;
}
