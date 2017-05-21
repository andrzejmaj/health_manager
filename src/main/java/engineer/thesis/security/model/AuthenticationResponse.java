package engineer.thesis.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponse implements Serializable {

    private final long serialVersionUID = 1L;

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken(String token) {
        return token;
    }
}
