package engineer.thesis.core.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private final Long id;
    private final String email;
    private final String role;

    public UserDTO(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
