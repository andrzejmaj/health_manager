package engineer.thesis.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private final Long id;
    private final String email;
    private final String role;
    private final String imgUrl;

    public UserDTO(Long id, String email, String role, String imgUrl) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.imgUrl = imgUrl;
    }
}
