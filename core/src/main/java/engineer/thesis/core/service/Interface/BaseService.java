package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.UserRole;
import engineer.thesis.core.security.model.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

public interface BaseService {

    default SecurityUser getCurrentUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    default Boolean checkCurrentUserRoleInclusion(UserRole... roles) {
        SecurityUser loggedUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication();
        if (loggedUser.getUserRole() == UserRole.ROLE_ADMIN) {
            return true;
        }
        for (UserRole role : roles) {
            if (role == loggedUser.getUserRole()) {
                return true;
            }
        }
        return false;
    }

}
