package engineer.thesis.core.security;

import engineer.thesis.core.model.User;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.model.SecurityUserFactory;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {


    private TokenUtils tokenUtils;

    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = "Authorization";
        String tokenPrefix = "Bearer ";
        String token = request.getHeader(tokenHeader);

        logger.info("Token: " + token);

        if (token != null) {
            token = token.replace(tokenPrefix, "");
            String email;
            try{
                email = tokenUtils.getUsername(token);
            } catch (ExpiredJwtException e){
                throw e;
            }

            logger.info("Email in token: " + email);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByEmail(email);

                logger.info("User found: " + user);

                if (user != null) {
                    UserDetails userDetails = SecurityUserFactory.create(user);
                    if (tokenUtils.validateToken(token, userDetails)) {

                        logger.info("Token validated successfully.");
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(userDetails);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
