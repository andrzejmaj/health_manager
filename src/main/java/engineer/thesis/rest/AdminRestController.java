package engineer.thesis.rest;

import engineer.thesis.controller.AuthenticationRestController;
import engineer.thesis.model.User;
import engineer.thesis.model.UserRole;
import engineer.thesis.security.model.RegisterRequest;
import engineer.thesis.security.model.RegisterResponse;
import engineer.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kamil on 2017-04-07.
 */
@RestController
public class AdminRestController {


    public UserService userService;

    public AuthenticationRestController authenticationRestController;

    @Autowired
    public AdminRestController(UserService userService, AuthenticationRestController authenticationRestController) {
        this.userService = userService;
        this.authenticationRestController = authenticationRestController;
    }


    @RequestMapping("/admin/register_doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequest registerRequest, UserRole role) {
        System.out.println(registerRequest);

        Optional<User> userOptional = userService.registerNewUser(registerRequest, role);
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

    @RequestMapping(path = "/users" ,  method = RequestMethod.GET)
    public  List<User> getUsers() {
        List<User> u = new ArrayList<>();
        u.add(userService.findByEmail("john@doe.com").get());
        return u;
    }
}
