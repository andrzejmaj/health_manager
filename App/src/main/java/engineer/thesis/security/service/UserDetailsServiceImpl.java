package engineer.thesis.security.service;

import engineer.thesis.model.User;
import engineer.thesis.security.model.SecurityUser;
import engineer.thesis.security.model.SecurityUserFactory;
import engineer.thesis.service.IUserService;
import engineer.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email:" + email + "not found"));
        return SecurityUserFactory.create(user);
    }
}
