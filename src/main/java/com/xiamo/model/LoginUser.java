package com.xiamo.model;

import com.xiamo.entity.WxUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * The type Login user.
 *
 * @Author: AceXiamo
 * @ClassName: LoginUser
 * @Date: 2023 /3/3 22:45
 */
@Data
public class LoginUser implements UserDetails {

    /**
     * The Username.
     */
    private String username;
    /**
     * The Password.
     */
    private String password;
    /**
     * The Token key.
     */
    private String tokenKey;
    /**
     * The Wx user.
     */
    private WxUser wxUser;

    /**
     * Instantiates a new Login user.
     *
     * @param username the username
     * @param password the password
     */
    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
