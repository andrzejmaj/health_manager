package engineer.thesis.controller;

import engineer.thesis.model.User;
import engineer.thesis.security.TokenUtils;
import engineer.thesis.security.model.AuthenticationRequest;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.security.model.AuthenticationResponse;
import engineer.thesis.security.model.SecurityUser;
import engineer.thesis.security.model.SecurityUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mobile.device.Device;

@RestController
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtil;


    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest, Device device) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        SecurityUser SecurityUser = SecurityUserFactory.create(user);

        String token = tokenUtil.generateToken(SecurityUser, device);


        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


}


