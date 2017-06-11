package engineer.thesis.core.repository;

import engineer.thesis.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
