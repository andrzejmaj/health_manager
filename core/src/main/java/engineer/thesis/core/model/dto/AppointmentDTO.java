package engineer.thesis.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private Long timeSlotId;
    private Boolean tookPlace;
    private Integer officeNumber;
    private String data;
}