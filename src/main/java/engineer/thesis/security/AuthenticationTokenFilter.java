package engineer.thesis.security;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.SecurityUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private String tokenHeader = "Authorization";
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(tokenHeader);
        System.out.println("token in authenticationTokenFilter " + token);
        String email = tokenUtils.getUsername(token);
        System.out.println("email in authenticationTokenFilter " + email);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                UserDetails userDetails = SecurityUserFactory.create(user);
                if (tokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }

        filterChain.doFilter(request, response);
    }
}
