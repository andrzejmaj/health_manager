package engineer.thesis.core.service.Interface;

import engineer.thesis.core.exception.AccessDeniedException;
import engineer.thesis.core.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public abstract class BaseService {

    protected void checkCurrentUser(Long id) throws AccessDeniedException {
        if (!Objects.equals(getCurrentUser().getId(), id)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    protected SecurityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) authentication.getPrincipal();
    }
}
