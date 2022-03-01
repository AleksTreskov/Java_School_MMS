package edu.aleksandrTreskov.mms.security;

import edu.aleksandrTreskov.mms.entity.Client;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
public class SecurityUser implements UserDetails {
    private final String username;
    private final String password;
    private final boolean isActive;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String username, String password, boolean isActive, List<SimpleGrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
    public static UserDetails fromUser(Client client){
        return new User(client.getEmail(), client.getPassword(),
                Objects.equals(client.isDeleted(), false),
                Objects.equals(client.isDeleted(), false),
                Objects.equals(client.isDeleted(), false),
                Objects.equals(client.isDeleted(), false),
                List.of(client.getRole()));
    }
}
