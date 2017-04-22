package engineer.thesis.controller;

import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.security.TokenUtils;
import engineer.thesis.security.model.*;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.service.UserDetailsServiceImpl;
import engineer.thesis.service.UserService;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mobile.device.Device;

import java.util.Optional;

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

        SecurityUser securityUser = userDetailsService.loadUserByUsername(
                authenticationRequest.getEmail()
        );

        String token = tokenUtil.generateToken(securityUser, device);


        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
        public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        System.out.println(registerRequest);


        Optional<User> userOptional = userService.registerNewUser(registerRequest);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
//        User user = new User();
//        user.setEmail(registerRequest.getEmail());
//        user.setPassword(registerRequest.getPassword());
//        user.setRole(UserRole.ROLE_PATIENT);
//        userRepository.save(user);
        return ResponseEntity.ok(new RegisterResponse("random Hindus"));
    }

}


