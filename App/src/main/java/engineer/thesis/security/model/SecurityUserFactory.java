package engineer.thesis.security.model;

import engineer.thesis.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andrzejmaj123 on 08/04/2017.
 */

public class SecurityUserFactory {

    private SecurityUserFactory() {

    }

    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }
}