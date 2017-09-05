package engineer.thesis.core.model.dto;

import lombok.Value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Value
public class TimeSlotDTO {
    Long id;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    Date startDateTime;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    Date endDateTime;

    boolean availableForSelfSign;
}
