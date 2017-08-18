package engineer.thesis.core.security.model;

import engineer.thesis.core.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    UserRole role;
}
