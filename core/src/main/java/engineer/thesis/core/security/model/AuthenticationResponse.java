package engineer.thesis.core.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponse implements Serializable {

    private final long serialVersionUID = 1250166508152483573L;

    private final String token;

}
