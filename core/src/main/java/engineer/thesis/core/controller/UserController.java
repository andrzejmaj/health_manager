package engineer.thesis.core.controller;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.exception.TokenExpiredException;
import engineer.thesis.core.model.dto.*;
import engineer.thesis.core.model.entity.UserRole;
import engineer.thesis.core.security.TokenUtils;
import engineer.thesis.core.security.model.AuthenticationRequest;
import engineer.thesis.core.security.model.AuthenticationResponse;
import engineer.thesis.core.security.model.SecurityUser;
import engineer.thesis.core.security.model.UpdatePasswordRequest;
import engineer.thesis.core.service.Implementation.PatientService;
import engineer.thesis.core.service.Interface.IUserService;
import engineer.thesis.core.utils.MailService;
import engineer.thesis.core.validator.RegisterUserGroup;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private static final String NOT_ALLOWED_MESSAGE = "You are not allowed to perform this operation";
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private TokenUtils tokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PatientService patientService;

    @RequestMapping(path = RequestMappings.USERS.LOGIN, method = RequestMethod.POST)
    public ResponseEntity<?> login(
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

        return new ResponseEntity<>(new AuthenticationResponse(tokenUtil.generateToken((SecurityUser) securityUser, myDevice)),
                HttpStatus.OK);
    }

    @RequestMapping(path = RequestMappings.USERS.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody @Validated(RegisterUserGroup.class) RegisterRequestDTO registerRequest) {
        try {
            userService.registerUserByRole(registerRequest, UserRole.ROLE_PATIENT, false);
            return new ResponseEntity<>("Registration successful", HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(path = RequestMappings.USERS.REGISTER_ON_BEHALF, method = RequestMethod.POST)
    public ResponseEntity<?> registerOnBehalf(@RequestBody @Valid RegisterOnBehalfRequestDTO request) {
        try {
            userService.registerUserByRole(request, request.getRole(), true);
            return new ResponseEntity<>("Registration successful", HttpStatus.OK);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = RequestMappings.PATIENTS.REGISTER, method = RequestMethod.POST)
    public ResponseEntity<?> registerUserless(@RequestBody @Valid PatientDetailsDTO patientDetails) {
        try {
            return new ResponseEntity<>(patientService.register(patientDetails), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
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
        try {
            ResetPasswordDTO data = userService.resetUserPassword(email);
            mailService.send(mailService.constructResetTokenEmail(request.getPathInfo(),
                    request.getLocale(), data.getToken(), data.getUserId(), data.getEmail()));
            return new ResponseEntity<>(userService.resetUserPassword(email), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update user's password using token which was sent to user after reset
     *
     * @param newPassword - new password provided by user
     * @param id          - user's id
     * @param token       - token which user received
     * @return - message if password was updated successfully
     */

    @RequestMapping(path = RequestMappings.USERS.UPDATE_PASSWORD_WITH_TOKEN, method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody String newPassword,
                                            @RequestParam("id") Long id,
                                            @RequestParam("token") String token) {
        try {
            return new ResponseEntity<>(userService.changeUserPasswordWithToken(id, token, newPassword), HttpStatus.OK);
        } catch (TokenExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
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
        try {
            return new ResponseEntity<>(userService.changeUserPassword(getCurrentUser().getUsername(),
                    updatePasswordData.getPassword()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update user's email
     *
     * @param id    - id of user
     * @param email - user's new email
     * @return - message if email was updated successfully
     */

//    @RequestMapping(path = RequestMappings.USERS.ACTIVATE, method = RequestMethod.POST)
//    public ResponseEntity<?> activateUser(@PathVariable Long id) {
//        return new ResponseEntity<Object>(userService)
//    }
    @RequestMapping(path = RequestMappings.USERS.UPDATE_EMAIL, method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody String email) {
        if (!canPerformUserAction(id, getCurrentUser())) {
            return new ResponseEntity<>(NOT_ALLOWED_MESSAGE, HttpStatus.FORBIDDEN);
        }
        try {
            return new ResponseEntity<>(userService.updateUserEmail(id, email), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        if (!canPerformUserAction(userDTO.getId(), getCurrentUser())) {
            return new ResponseEntity<>(NOT_ALLOWED_MESSAGE, HttpStatus.FORBIDDEN);
        }
        try {
            return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }

    private boolean canPerformUserAction(Long id, SecurityUser currentUser) {
        return id.equals(currentUser.getId()) || currentUser.getUserRole() == UserRole.ROLE_ADMIN;
    }

    private boolean canPerformUserAction(String email, SecurityUser currentUser) {
        return email.equals(currentUser.getEmail()) || currentUser.getUserRole() == UserRole.ROLE_ADMIN;
    }

}
