package com.adaip.jobsapi.controller;

import com.adaip.jobsapi.dto.UserLoginRequestDTO;
import com.adaip.jobsapi.model.User;
import com.adaip.jobsapi.service.AuthService;
import com.adaip.jobsapi.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerUser(@Valid @RequestBody User user) {
        Long userId = authService.addUser(user);
        return userId;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDTO userLoginRequest) {
        String token = authService.authenticateUser(userLoginRequest);
        return token;
    }
}
