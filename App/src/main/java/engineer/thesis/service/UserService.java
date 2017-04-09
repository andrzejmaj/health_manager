package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by Kamil on 2017-04-08.
 */

public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
