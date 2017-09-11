package engineer.thesis.core.security.service;


import engineer.thesis.core.model.User;
import engineer.thesis.core.model.UserRole;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.GoogleOAuth.AccessTokenValidationResult;
import engineer.thesis.core.security.GoogleOAuth.AccessTokenValidator;
import engineer.thesis.core.security.GoogleOAuth.OAuthProperties;
import engineer.thesis.core.security.model.SecurityUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GoogleTokenServices implements ResourceServerTokenServices {

    @Autowired
    private OAuthProperties oAuthProperties;

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    @Autowired
    private AccessTokenValidator tokenValidator;

    public GoogleTokenServices(AccessTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        AccessTokenValidationResult validationResult = tokenValidator.validate(accessToken);
        if (!validationResult.isValid()) {
            throw new UnapprovedClientAuthenticationException("The token is not intended to be used for this application.");
        }
        Map<String, ?> tokenInfo = validationResult.getTokenInfo();
        return createAuthentication(tokenInfo);
    }

    private OAuth2Authentication createAuthentication(Map<String, ?> tokenInfo) {
        OAuth2Request request = tokenConverter.extractAuthentication(tokenInfo).getOAuth2Request();
        Authentication authentication = getAuthenticationToken(tokenInfo);
        return new OAuth2Authentication(request, authentication);
    }

    private Authentication getAuthenticationToken(Map<String, ?> tokenInfo) {
        String email = (String) tokenInfo.get("email");
        if (email == null) {
            throw new InvalidTokenException("Token doesn't contain email");
        }

        User user = userRepository.findByEmail(email);
        UserDetails userDetails;
        if (user == null) {
            user = new User();
            user.setRole(UserRole.ROLE_PATIENT);
            user.setEmail(email);

        }

        userDetails = SecurityUserFactory.create(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}