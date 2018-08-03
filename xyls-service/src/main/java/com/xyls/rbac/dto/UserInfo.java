package com.xyls.rbac.dto;

import com.xyls.rbac.domain.SysResource;
import com.xyls.rbac.domain.SysRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserInfo implements UserDetails {

    private String userId;

    private String userName;

    private String password;

    private List<SysRole> sysRoles;

    private List<SysResource> sysResources;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
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
