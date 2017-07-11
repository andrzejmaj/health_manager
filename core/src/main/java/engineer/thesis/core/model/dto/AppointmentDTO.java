package engineer.thesis.model.dto;

import lombok.Value;

@Value
public class AppointmentDTO {
    Long id;
    Long timeSlotId;
    Boolean tookPlace;
    Integer officeNumber;
    String data;
}