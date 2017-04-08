package engineer.thesis.model.json;

import lombok.Data;

/**
 * Created by Kamil on 2017-04-08.
 */
@Data
public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
