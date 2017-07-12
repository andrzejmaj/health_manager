package engineer.thesis.core.model.dto;

import lombok.Value;

import java.util.Date;

@Value
public class TimeSlotDTO {
    Long id;
    Long doctorId;
    Date startDateTime;
    Date endDateTime;
    Long TimeSlotTypeId;
}
