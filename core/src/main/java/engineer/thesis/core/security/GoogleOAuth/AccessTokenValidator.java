package engineer.thesis.core.security.GoogleOAuth;

import org.springframework.stereotype.Component;

@Component
public interface AccessTokenValidator {
    AccessTokenValidationResult validate(String accessToken);
}
