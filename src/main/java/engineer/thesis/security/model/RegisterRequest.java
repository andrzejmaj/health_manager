package engineer.thesis.security.model;

import engineer.thesis.model.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    UserRole role;

    public RegisterRequest(String firstName, String lastName, String email, UserRole userRole, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = userRole;
    }
}
