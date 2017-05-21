package engineer.thesis.security.model;

import engineer.thesis.model.UserRole;
import lombok.Data;

/**
 * Created by Kamil on 2017-04-21.
 */

@Data
public class RegisterRequest {
    String fisrtName;
    String surName;
    String email;
    String password;
    UserRole role;

    public RegisterRequest() {

    }

    public RegisterRequest(String fisrtName, String surName, String email, UserRole userRole) {
        this.fisrtName = fisrtName;
        this.role = userRole;
        this.surName = surName;
        this.email = email;
    }
}
