package engineer.thesis.security.model;



import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResponse implements Serializable {

    private final long serialVersionUID = 8967452301L;

    private String XD;

    public RegisterResponse(String XD) {
        this.XD = XD;
    }


}
