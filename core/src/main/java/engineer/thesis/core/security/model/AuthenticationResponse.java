package engineer.thesis.core.security.model;

import engineer.thesis.core.model.UserRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class AuthenticationResponse implements Serializable {

    private final long serialVersionUID = 1250166508152483573L;

    private final String token;

}
