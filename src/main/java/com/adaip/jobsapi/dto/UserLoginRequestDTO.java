package com.adaip.jobsapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginRequestDTO {

    @NotNull(message = "Username is required.")
    @Size(min = 1, message = "Username is required.")
    private String username;

    @NotNull(message = "Password is required.")
    @Size(min = 1, message = "Password is required.")
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
