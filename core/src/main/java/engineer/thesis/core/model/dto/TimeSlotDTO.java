package engineer.thesis.core.model.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TimeSlotDTO {
    Long id;
    Long doctorId;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
