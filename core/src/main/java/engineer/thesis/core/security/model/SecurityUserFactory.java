package engineer.thesis.core.security.model;

import engineer.thesis.core.model.entity.User;

public class SecurityUserFactory {

    private SecurityUserFactory() {

    }

    public static SecurityUser create(User user) {
        return new SecurityUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getIsActive()
        );
    }
}