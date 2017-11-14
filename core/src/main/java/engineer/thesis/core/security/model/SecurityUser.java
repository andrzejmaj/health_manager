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
    private UserRole userRole;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean isActive;

    SecurityUser(Long id, String email, String password, UserRole role, Boolean isActive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = role;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(String.valueOf(userRole)));
        return  auths;
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
