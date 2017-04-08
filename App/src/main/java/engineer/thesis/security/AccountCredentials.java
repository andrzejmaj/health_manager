package engineer.thesis.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Kamil on 2017-04-07.
 */
public class AccountCredentials {
    private String password;

    private String username;
    @RequestMapping(value = "${jwt.route.authentication.path}",method = GET)
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
