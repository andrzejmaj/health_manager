package engineer.thesis.core.service.Interface;

import engineer.thesis.core.model.entity.User;
import engineer.thesis.core.model.entity.UserRole;
import engineer.thesis.core.repository.UserRepository;
import engineer.thesis.core.security.model.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

public interface BaseService {

    default SecurityUser getCurrentLoggedUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    default User getCurrentLoggedUserEntity(UserRepository userRepository) {
        return userRepository.findOne(((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    default Boolean checkCurrentUserRoleInclusion(UserRole... roles) {
        SecurityUser loggedUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication();
        if (loggedUser.getRole() == UserRole.ROLE_ADMIN) {
            return true;
        }
        for (UserRole role : roles) {
            if (role == loggedUser.getRole()) {
                return true;
            }
        }
        return false;
    }

}
