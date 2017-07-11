package engineer.thesis.model.dto;

import lombok.Value;
import java.util.Date;

@Value
public class MedicalCheckupDTO {
    Long id;
    Long doctorId;
    Date creationDate;
    Long timeSlotType;
}
