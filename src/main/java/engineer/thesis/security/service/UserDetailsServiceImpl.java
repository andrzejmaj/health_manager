package engineer.thesis.security.service;

import engineer.thesis.model.User;
import engineer.thesis.security.model.SecurityUser;
import engineer.thesis.security.model.SecurityUserFactory;
import engineer.thesis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Autowired
    public UserDetailsServiceImpl(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email:" + email + "not found"));
        System.out.println("userdetails found user:" + user);
        return SecurityUserFactory.create(user);
    }
}
