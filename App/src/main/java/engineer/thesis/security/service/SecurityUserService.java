package engineer.thesis.security.service;

import engineer.thesis.model.User;
import engineer.thesis.repository.UserRepository;
import engineer.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Kamil on 2017-04-08.
 */
public class SecurityUserService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email).orElseThrow(
                () =>  new UsernameNotFoundException(
                        "User with email:" + email + "not found"));





    }
}
