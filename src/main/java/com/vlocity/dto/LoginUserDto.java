package com.vlocity.dto;

import java.io.Serializable;

public class LoginUserDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1131037420703798267L;

    private String username;
    private String password;

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
}
