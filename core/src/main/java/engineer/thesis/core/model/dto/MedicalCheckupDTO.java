package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCheckupDTO {
    Long id;
    Long patientId;
    Date creationDate;
    Long timeSlotType;
    HashMap<String,Object> data;
}
