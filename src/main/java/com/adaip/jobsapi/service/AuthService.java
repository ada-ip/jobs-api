package com.adaip.jobsapi.service;

import com.adaip.jobsapi.dto.UserLoginRequestDTO;
import com.adaip.jobsapi.exception.UniqueFieldException;
import com.adaip.jobsapi.model.User;
import com.adaip.jobsapi.repository.UserRepository;
import com.adaip.jobsapi.util.JWTUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Long addUser(User user) {
        if(isUsernameRegistered(user.getUsername())) {
            throw new UniqueFieldException("username", "Username is already registered.");
        }
        if(isEmailRegistered(user.getEmail())) {
            throw new UniqueFieldException("email", "Email is already registered.");
        }
        user.setPassword(hashPassword(user.getPassword()));
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
            return false;
        }
        return comparePassword(userLoginRequest.getPassword(), user.getPassword());
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Boolean comparePassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
