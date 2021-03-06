package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalCheckupDTO {
    private Long id;
    @NotNull
    private Long formId;
    @Valid
    @NotNull
    @NotEmpty
    private List<MedicalCheckupValueDTO> medicalCheckupValues;
    private Date createdDate;
    private Date lastModifiedDate;
}
