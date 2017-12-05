package engineer.thesis.core.security;

import engineer.thesis.core.controller.RequestMappings;
import engineer.thesis.core.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
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
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**")
                    .permitAll()
                .antMatchers("/medcom/**")
                    .hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(
                        RequestMappings.FORMS.FORMS,                                  //k
                        RequestMappings.FORMS.FORM,                                   //k
                        RequestMappings.FORMS.FORM_DEFAULTS,                          //k
                        RequestMappings.FORMS.FORMS_DEFAULTS_ID,                      //k
                        RequestMappings.CHECKUP.PATIENT_CHECKUPS,                     //k
                        RequestMappings.CHECKUP.CHECKUP_ID,                           //k
                        RequestMappings.DRUGS.DRUGS,                                  //k
                        RequestMappings.DRUGS.DRUGS_ID,                               //k
                        RequestMappings.MEDICAL.PATIENT_MEDICAL,                      //k
                        RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS,                  //k
                        RequestMappings.PRESCRIPTIONS.PRESCRIPTIONS_ID,               //k
                        RequestMappings.HISTORY.PATIENT_HISTORY,                      //k
                        RequestMappings.HISTORY.PATIENT_HISTORY_ID,                   //k
                        RequestMappings.CURRENT_CONDITION.CURRENT_CONDITION,          //k
                        RequestMappings.CURRENT_CONDITION.CURRENT_CONDITION_ID        //k
                ).hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers(
                        RequestMappings.PATIENTS.PATIENTS,                            //k
                        RequestMappings.PATIENTS.PATIENTS_EMAIL,                      //k
                        RequestMappings.PATIENTS.PATIENTS_ID,                         //k
                        RequestMappings.PATIENTS.REGISTER,                            //k
                        RequestMappings.PATIENTS.PERS_DETAILS,                        //k
                        RequestMappings.PATIENTS.EMERGENCY,                           //k
                        RequestMappings.USERS.REGISTER_ON_BEHALF                      //k
                ).hasAnyRole("DOCTOR", "ADMIN", "RECEPTIONIST")
                .antMatchers(
                        RequestMappings.USERS.UPDATE_PASSWORD,
                        RequestMappings.USERS.UPDATE_EMAIL,
                        RequestMappings.ACCOUNTS.MY_PERS_DETAILS
                ).authenticated()
                .antMatchers(
                        RequestMappings.ACCOUNTS.MY_EMERGENCY_CONTACT,
                        RequestMappings.ACCOUNTS.MY_MEDICAL,
                        RequestMappings.HISTORY.MY_HISTORY,
                        RequestMappings.PRESCRIPTIONS.MY_PRESCRIPTIONS
                ).hasAnyRole("PATIENT")
                .antMatchers(
                        RequestMappings.USERS.LOGIN,
                        RequestMappings.USERS.REGISTER,
                        RequestMappings.USERS.RESET_PASSWORD,
                        RequestMappings.USERS.UPDATE_PASSWORD_WITH_TOKEN
                ).permitAll()
                .antMatchers(
                        RequestMappings.USERS.ACTIVATE,
                        RequestMappings.ACCOUNTS.ACCOUNTS_PICTURE
                ).denyAll()
                .antMatchers(
                        "/**"
                ).permitAll();


        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }
}
