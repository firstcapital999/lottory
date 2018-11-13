package com.thinkive.common.authority.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @Describe 用户验证实体类
 * @Author dengchangneng
 * @CreateTime 2017年10月9日09:42:27
 */
public class SecurityUser extends User implements UserDetails {

    public SecurityUser(User user) {
        if(user != null )
        {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setNickName(user.getNickName());
            this.setPassword(user.getPassword());
            this.setRegistrationTime(user.getRegistrationTime());
            this.setRoles(user.getRoles());
        }
    }

    //获取权限集合
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
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
