package engineer.thesis.model.dto;

import lombok.Value;
import org.springframework.http.ResponseEntity;

@Value
public class ErrorDTO extends ResponseEntity {
    String reason;
}
