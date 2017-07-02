package engineer.thesis.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.sun.org.apache.regexp.internal.RE;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.PersonalDetailDTO;
import engineer.thesis.model.dto.ResetPasswordDTO;
import engineer.thesis.model.dto.ResponseDTO;
import engineer.thesis.security.TokenUtils;
import engineer.thesis.security.model.*;
import engineer.thesis.service.UserService;
import engineer.thesis.utils.MailService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.rmi.NotBoundException;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenUtils tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String NOT_ALLOWED_MESSAGE = "You are not allowed to perform this operation";
    private static final String NOT_FOUND = "Something was not found";

    //TODO:
    // 1. Probably only UserService should be autowired here
    // 2. Make class with responses
    // 3. Make UserService responsible for responses
    // 4. Add some loggers

    /**
     * Login user into application
     *
     * @param authenticationRequest (email, password)
     * @return created user's token
     */

    @RequestMapping(path = RequestMappings.USERS.LOGIN, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) {

        if (!userService.findByEmail(authenticationRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails securityUser = userDetailsService.loadUserByUsername(
                authenticationRequest.getEmail()
        );

        //TODO:
        // change this later
        // find out how to use this device thing
        Device myDevice = new Device() {
            @Override
            public boolean isNormal() {
                return true;
            }

            @Override
            public boolean isMobile() {
                return false;
            }

            @Override
            public boolean isTablet() {
                return false;
            }
        };

        return new ResponseEntity<>(new AuthenticationResponse(tokenUtil.generateToken(securityUser, myDevice)),
                HttpStatus.OK);
    }

    /**
     * Register new users in system
     *
     * @param registerRequest - data of new user
     * @return - message if user was registered successfully
     */

    @RequestMapping(path = RequestMappings.USERS.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        ResponseDTO response = userService.registerNewUser(registerRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Reset user's password
     *
     * @param request - metadata
     * @param email   - users email
     * @return message if success
     */

    @RequestMapping(path = RequestMappings.USERS.RESET_PASSWORD, method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestBody String email) {


        ResponseDTO response = userService.resetUserPassword(email);

        if (response.getStatus() == HttpStatus.OK) {
            ResetPasswordDTO data = (ResetPasswordDTO) response.getData();
            mailService.send(mailService.constructResetTokenEmail(request.getPathInfo(),
                    request.getLocale(), data.getToken(), data.getUserId(), data.getEmail()));
        }

        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Update user's password using token which was sent to user after reset
     *
     * @param newPassword - new password provided by user
     * @param email       - user's email
     * @param token       - token which user received
     * @return - message if password was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_PASSWORD_WITH_TOKEN, method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody String newPassword,
                                            @RequestParam("email") String email,
                                            @RequestParam("token") String token) {

        //TODO:
        // inside changeUserPassword there should be some
        // validator methods (check if user can change password,
        // is new password same as old one etc.) to
        // return proper response (not only operation successful)
        ResponseDTO response = userService.changeUserPasswordWithToken(email, token, newPassword);
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Update user's password
     *
     * @param updatePasswordData - old password, and new passwords
     * @return - message if password was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_PASSWORD, method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest updatePasswordData) {
        //TODO:
        // Same as above. Add validators and return proper response
        ResponseDTO response = userService.changeUserPassword(getCurrentUser().getUsername(), updatePasswordData.getPassword());
        return new ResponseEntity<>(response, response.getStatus());
    }

    /**
     * Update user's email
     *
     * @param id    - id of user
     * @param email - user's new email
     * @return - message if email was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_EMAIL, method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody String email) {

        if (!canPerformUserAction(id, getCurrentUser())) {
            return new ResponseEntity<>(NOT_ALLOWED_MESSAGE, HttpStatus.FORBIDDEN);
        }

        ResponseDTO response = userService.updateUserEmail(id, email);
        return new ResponseEntity<>(userService.updateUserEmail(id, email), response.getStatus());
    }

    @RequestMapping(path = RequestMappings.USERS.USER_PERSONAL_DETAILS, method = RequestMethod.GET)
    public ResponseEntity<?> getPersonalDetails(@PathVariable Long id){
        try {
            ResponseDTO responseDTO = userService.getPersonalDetails(id);
            return ResponseEntity.ok(responseDTO);
        } catch (NotFoundException | NotBoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = RequestMappings.USERS.USER_PERSONAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<?> savePersonalDetails(@PathVariable Long id,
                                                 @RequestBody PersonalDetailDTO personalDetail){
        try {
            ResponseDTO responseDTO = userService.savePersonalDetails(personalDetail, id);
            return ResponseEntity.ok(responseDTO);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<Object>(NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    private SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }

    private boolean canPerformUserAction(Long id, SecurityUser currentUser) {
        return id.equals(currentUser.getId()) || currentUser.getUserRole() == UserRole.ADMIN;
    }

    private boolean canPerformUserAction(String email, SecurityUser currentUser) {
        return email.equals(currentUser.getEmail()) || currentUser.getUserRole() == UserRole.ADMIN;
    }

}