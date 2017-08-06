package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    Long id;
    Long timeSlotId;
    Boolean tookPlace;
    Integer officeNumber;
    String data;
}