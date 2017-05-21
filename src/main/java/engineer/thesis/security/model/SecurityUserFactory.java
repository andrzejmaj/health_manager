package engineer.thesis.security.model;

import engineer.thesis.model.User;

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