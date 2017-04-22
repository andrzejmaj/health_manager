package engineer.thesis.security.model;

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

    public RegisterRequest() {

    }

    public RegisterRequest(String fisrtName, String surName, String email) {
        this.fisrtName = fisrtName;
        this.surName = surName;
        this.email = email;
    }
}
