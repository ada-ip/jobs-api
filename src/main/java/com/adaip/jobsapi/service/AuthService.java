package com.adaip.jobsapi.service;

import com.adaip.jobsapi.dto.UserLoginRequestDTO;
import com.adaip.jobsapi.exception.DBFieldException;
import com.adaip.jobsapi.model.User;
import com.adaip.jobsapi.repository.UserRepository;
import com.adaip.jobsapi.util.JWTUtil;
import com.adaip.jobsapi.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoderUtil passwordEncoderUtil, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
        this.jwtUtil = jwtUtil;
    }

    public Long addUser(User user) {
        if(isUsernameRegistered(user.getUsername())) {
            throw new DBFieldException("username", "Username is already registered.");
        }
        if(isEmailRegistered(user.getEmail())) {
            throw new DBFieldException("email", "Email is already registered.");
        }
        user.setPassword(passwordEncoderUtil.hashPassword(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    public String authenticateUser(UserLoginRequestDTO userLoginRequest) {
        String token = "";
        Boolean isUserValid = isUserValid(userLoginRequest);
        if(isUserValid) {
            User user = userRepository.findByUsername(userLoginRequest.getUsername());
            token = jwtUtil.generateToken(user.getId(), user.getUsername());
        }
        return token;
    }

    public Boolean isEmailRegistered(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public Boolean isUsernameRegistered(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public Boolean isUserValid(UserLoginRequestDTO userLoginRequest){
        User user = userRepository.findByUsername(userLoginRequest.getUsername());
        if(user == null) {
            throw new DBFieldException("username", "Username is not registered.");
        }
        if(!passwordEncoderUtil.comparePasswords(userLoginRequest.getPassword(), user.getPassword())) {
            throw new DBFieldException("password", "Wrong password.");
        }
        return true;
    }
}
