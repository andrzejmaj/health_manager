package engineer.thesis.service;

import engineer.thesis.model.User;
import engineer.thesis.security.model.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    Optional<User> findByEmail(String email);

    Optional<User> registerNewUser(RegisterRequest registerRequest);
}
