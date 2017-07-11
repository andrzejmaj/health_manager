package engineer.thesis.core.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequest {

    private String passwordCurrent;
    private String password;
    private String passwordConfirmation;

    public UpdatePasswordRequest(String passwordCurrent, String password, String passwordConfirmation) {
        this.passwordCurrent = passwordCurrent;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordCurrent() {
        return passwordCurrent;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
