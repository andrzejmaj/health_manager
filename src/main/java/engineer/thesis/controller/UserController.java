package engineer.thesis.controller;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.model.dto.ErrorDTO;
import engineer.thesis.security.TokenUtils;
import engineer.thesis.security.model.*;
import engineer.thesis.service.UserService;
import engineer.thesis.utils.MailService;
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
     *
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

        //TODO:
        // role should not be fixed as patient
        // figure out how to change it
        registerRequest.setRole(UserRole.PATIENT);
        
        //TODO:
        // change it: contoller should only return response provided by UserService
        Optional<User> userOptional = userService.registerNewUser(registerRequest);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User was registered successfully", HttpStatus.OK);
    }

    /**
     * Reset user's password
     *
     * @param request - metadata
     * @param email - users email
     * @return message if success
     */

    @RequestMapping(path = RequestMappings.USERS.RESET_PASSWORD, method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                           @RequestBody String email) {

        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //TODO:
        // add to reset password token table isActive
        // property and disable it after used

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user.get(), token);

        //TODO:
        // probably we should move mail service to UserService

        mailService.send(mailService.constructResetTokenEmail(request.getPathInfo(),
                request.getLocale(), token, user.get()));

        return new ResponseEntity<>("Password was reset successfully", HttpStatus.OK);
    }

    /**
     * Update user's password using token which was sent to user after reset
     *
     * @param newPassword - new password provided by user
     * @param email - user's email
     * @param token - token which user received
     * @return - message if password was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_PASSWORD_WITH_TOKEN, method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody String newPassword,
                                            @RequestParam("email") String email,
                                            @RequestParam("token") String token) {
        //TODO:
        // 1. move to UserService
        // 2. make this method private
        // 3. remove it from UserService interface
        if (!userService.isResetPasswordTokenValid(email, token)) {
            return new ResponseEntity<>("Token is not valid", HttpStatus.FORBIDDEN);
        }

        //TODO:
        // inside changeUserPassword there should be some
        // validator methods (check if user can change password,
        // is new password same as old one etc.) to
        // return proper response (not only operation successful)
        userService.changeUserPassword(email, newPassword);

        return new ResponseEntity<>("Password was updated successfully", HttpStatus.OK);
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
        // Add some loggers f.e to see authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        //TODO:
        // Same as above. Add validators and return proper response
        userService.changeUserPassword(securityUser.getUsername(), updatePasswordData.getPassword());

        return new ResponseEntity<>("Password was updated successfully", HttpStatus.OK);
    }

    /**
     * Update user's email
     *
     * @param id - id of user
     * @param email - user's new email
     * @return - message if email was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_EMAIL, method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody String email) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        //TODO:
        // Probably should be moved to  UserService
        if (!userService.canPerformUserAction(id, securityUser)) {
            return new ResponseEntity<>("You are not allowed to perform this action", HttpStatus.FORBIDDEN);
        }

        //TODO:
        // validate if new email is not as old one
        userService.updateUserEmail(id, email);

        return new ResponseEntity<>("Email was updated successfully", HttpStatus.OK);
    }
}
