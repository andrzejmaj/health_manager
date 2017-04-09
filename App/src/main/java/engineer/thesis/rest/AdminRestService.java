package engineer.thesis.rest;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static UserRepository userRepository;

    @RequestMapping(path = "/users" ,  method = RequestMethod.GET)
    public static List<User> getUsers() {
        return userRepository.findAll();
    }
}
