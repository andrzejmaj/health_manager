package engineer.thesis.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponse implements Serializable {

    private final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    
}
