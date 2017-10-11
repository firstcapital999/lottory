package com.thinkive.common.authority.util;

import com.thinkive.lottery.entity.Role;
import com.thinkive.lottery.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by thinkive on 2017/10/9.
 */
public class SecurityUser extends User implements UserDetails {

    public SecurityUser(User user) {
        if(user != null )
        {
            this.setId(user.getId());
            this.setUserName(user.getUserName());
            this.setNickName(user.getNickName());
            this.setPassword(user.getPassword());
            this.setRegistrationTime(user.getRegistrationTime());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> userRoles = this.getRoles();

        if(userRoles != null )
        {
            for (Role role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleCode());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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
