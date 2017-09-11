package engineer.thesis.core.security;

import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.GoogleOAuth.GoogleOAuthFilter;
import engineer.thesis.core.security.service.GoogleTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private UserDetailsService userDetailsService;
    //used only for token initialization
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoogleTokenServices googleTokenServices;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter(tokenUtils, userRepository);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/users/login",
                "/users/register"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint);

//        http.authorizeRequests().antMatchers("/patients/*/history").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/patients/*/checkups").hasRole("PATIENT");
//        http.authorizeRequests().antMatchers("/patients/*/currentCondition").authenticated();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(oAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(authenticationTokenFilterBean(), GoogleOAuthFilter.class);

        http.headers().cacheControl();
    }

    public GoogleOAuthFilter oAuthFilter() {
        return new GoogleOAuthFilter(googleTokenServices, userRepository);
    }
}
