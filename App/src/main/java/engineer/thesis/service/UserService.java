package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by Kamil on 2017-04-08.
 */

public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        User user  = userRepository.findByEmail(email);

        if (user != null) {
            return Optional.of(user);
        }else{
            return Optional.empty();
        }

    }
}
