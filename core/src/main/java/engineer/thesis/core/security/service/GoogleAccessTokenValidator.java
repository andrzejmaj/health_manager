package engineer.thesis.core.security.service;

import engineer.thesis.core.security.GoogleOAuth.AccessTokenValidationResult;
import engineer.thesis.core.security.GoogleOAuth.AccessTokenValidator;
import engineer.thesis.core.security.GoogleOAuth.OAuthProperties;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Component
public class GoogleAccessTokenValidator implements AccessTokenValidator {

    @Autowired
    OAuthProperties oAuthProperties;

    private RestTemplate restTemplate = new RestTemplate();

    public GoogleAccessTokenValidator() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() == 400) {
                    throw new InvalidTokenException("The provided token is invalid");
                }
            }
        });
    }

    @Override
    public AccessTokenValidationResult validate(String accessToken) {
        Map<String, ?> response = getGoogleResponse(accessToken);
        boolean validationResult = validateResponse(response);
        return new AccessTokenValidationResult(validationResult, response);
    }

    private boolean validateResponse(Map<String, ?> response) throws AuthenticationException {
        String aud = (String) response.get("aud");
        return StringUtils.equals(aud, oAuthProperties.getClientId());
    }

    private Map<String, ?> getGoogleResponse(String accessToken) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());
        Map<String, String> variables = ImmutableMap.of("accessToken", accessToken);
        Map map = restTemplate.exchange(oAuthProperties.getCheckTokenUrl(), HttpMethod.GET, requestEntity, Map.class, variables).getBody();
        return (Map<String, Object>) map;
    }

}