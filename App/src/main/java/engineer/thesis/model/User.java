package engineer.thesis.model;

import lombok.Data;

import javax.persistence.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * Created by Kamil on 2017-04-07.
 */

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
