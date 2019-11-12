package com.vlocity.dto;

import java.io.Serializable;
import java.util.Set;

import com.vlocity.enumeration.UserType;

public class UserDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8322071214080852439L;

    private Long id;

    private String username;

    private String password;

    private Set<RoleDto> roles;

    private UserType userType;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
