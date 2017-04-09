package engineer.thesis.security.model;

public class AuthenticationResponse {

    private final String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken(String token) {
        return token;
    }
}
