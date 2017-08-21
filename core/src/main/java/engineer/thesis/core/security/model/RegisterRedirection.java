package engineer.thesis.core.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRedirection {

    String page;
    String token;

    public RegisterRedirection(String page, String token) {
        this.page = page;
        this.token = token;
    }
}
