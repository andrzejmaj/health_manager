package engineer.thesis.rest;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2017-04-07.
 */
@RestController
public class AdminRestService {

    @Autowired
    public  UserRepository userRepository;

    @RequestMapping(path = "/users" ,  method = RequestMethod.GET)
    public  List<User> getUsers() {
        List<User> u = new ArrayList<>();
        System.out.print(userRepository);
        u.add(userRepository.findByEmail("john@doe.com"));
        return u;
    }
}
