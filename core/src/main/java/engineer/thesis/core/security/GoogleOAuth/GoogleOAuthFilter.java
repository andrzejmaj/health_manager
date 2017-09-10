package engineer.thesis.core.security.GoogleOAuth;

import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.service.GoogleTokenServices;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GoogleOAuthFilter extends OncePerRequestFilter {

    private GoogleTokenServices googleTokenServices;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = "Authorization";
        String tokenPrefix = "Bearer ";
        String token = request.getHeader(tokenHeader);
        AccessTokenValidator accessTokenValidator;

        logger.info("Token google: " + token);

        if (token != null) {
            token = token.replace(tokenPrefix, "");
            OAuth2Authentication tokenUser;
            tokenUser = googleTokenServices.loadAuthentication(token);
            logger.info("Logged with google as: " + tokenUser.getName());
            SecurityContextHolder.getContext().setAuthentication(tokenUser.getUserAuthentication());

        }
        filterChain.doFilter(request, response);
    }

}
