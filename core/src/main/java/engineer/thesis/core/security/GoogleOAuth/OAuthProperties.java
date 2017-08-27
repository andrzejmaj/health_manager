package engineer.thesis.core.security.GoogleOAuth;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class OAuthProperties {
    @Value("${oauth.clientId}")
    private String clientId;
    @Value("${oauth.clientSecret}")
    private String clientSecret;
    @Value("${oauth.checkTokenUrl}")
    private String checkTokenUrl;
    @Value("${oauth.userInfoUrl}")
    private String userInfoUrl;

}
