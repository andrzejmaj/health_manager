package engineer.thesis.core.security.model;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RegisterResponse implements Serializable {

    private final long serialVersionUID = 8967452301L;

    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }


}
