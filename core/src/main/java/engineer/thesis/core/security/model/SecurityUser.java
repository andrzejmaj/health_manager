package engineer.thesis.core.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engineer.thesis.core.model.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class SecurityUser implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean isActive;

    SecurityUser(Long id, String email, String password, UserRole role, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(String.valueOf(role)));
        authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
