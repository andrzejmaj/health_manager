package engineer.thesis.core.rest;

import engineer.thesis.core.model.User;
import engineer.thesis.core.security.model.RegisterRequest;
import engineer.thesis.core.security.model.RegisterResponse;
import engineer.thesis.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminRestController {


    public UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/admin/register_doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequest registerRequest) {
        System.out.println(registerRequest);

//        User user = new User();
//        user.setEmail(registerRequest.getEmail());
//        user.setPassword(registerRequest.getPassword());
//        user.setRole(UserRole.PATIENT);
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
