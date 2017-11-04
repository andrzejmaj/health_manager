package engineer.thesis.core.model.dto;

import engineer.thesis.core.model.VisitPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    Long id;
    Long patientId;
    Long timeSlotId;
    Boolean tookPlace;
    Integer officeNumber;
    String data;
    VisitPriority priority;
}