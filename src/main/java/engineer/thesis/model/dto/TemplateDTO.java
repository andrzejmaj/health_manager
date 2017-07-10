package engineer.thesis.model.dto;

import lombok.Value;

@Value
public class TemplateDTO {
    Long id;
    Long doctorId;
    String template;
}
