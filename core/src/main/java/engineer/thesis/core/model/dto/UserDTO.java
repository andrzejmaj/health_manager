package engineer.thesis.core.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private final Long id;
    private final String email;
    private final String role;
    private final Boolean isActive;

}
