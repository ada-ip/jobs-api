package com.adaip.jobsapi.dto;

public class UserLoginRequestDTO {
    private String username;

    private String password;

    public UserLoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
