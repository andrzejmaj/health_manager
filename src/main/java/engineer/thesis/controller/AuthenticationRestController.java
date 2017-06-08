package engineer.thesis.controller;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.rest.AdminRestController;
import engineer.thesis.security.TokenUtils;
import engineer.thesis.security.model.AuthenticationRequest;
import engineer.thesis.security.model.AuthenticationResponse;
import engineer.thesis.security.model.RegisterRequest;
import engineer.thesis.security.service.UserDetailsServiceImpl;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    public AdminRestController adminRestController;

    @Autowired
    private MailService mailService;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest, Device device) {
        System.out.println(authenticationRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        User user = userRepository.findByEmail(authenticationRequest.getEmail());
//        SecurityUser SecurityUser = SecurityUserFactory.create(user);
        UserDetails securityUser = userDetailsService.loadUserByUsername(
                authenticationRequest.getEmail()
        );

        String token = tokenUtil.generateToken(securityUser, device);


        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {

        System.out.println("Register" + registerRequest);

        registerRequest.setRole(UserRole.PATIENT);

        Optional<User> userOptional = userService.registerNewUser(registerRequest);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Operation successful");

    }

    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                           @RequestParam("email") String email) {

        Optional<User> user = userService.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("USER NOT FOUND");
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user.get(), token);
        mailService.send(mailService.constructResetTokenEmail(request.getPathInfo(),
                request.getLocale(), token, user.get()));

        return ResponseEntity.ok("Operation successful");
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.GET)
    public String redirectFromChangePassword(@RequestParam("id") long id, @RequestParam("token") String token) {
        String result = userService.checkReceivedToken(id, token);
        if (result != null) {
            return "REDIRECT_TO_LOGIN_PAGE";
        }
        return "REDIRECT_TO_UPDATE_PASSWORD";
    }

    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam("password") String password) {
        User user =
                (User) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password);
        return "PASSWORD_UPDATED";
    }
}




