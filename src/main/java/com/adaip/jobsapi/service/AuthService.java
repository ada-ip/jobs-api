package com.adaip.jobsapi.service;

import com.adaip.jobsapi.exception.UniqueFieldException;
import com.adaip.jobsapi.model.User;
import com.adaip.jobsapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        if(isUsernameRegistered(user.getUsername())) {
            throw new UniqueFieldException("username", "Username is already registered.");
        }
        if(isEmailRegistered(user.getEmail())) {
            throw new UniqueFieldException("email", "Email is already registered.");
        }
        userRepository.save(user);
        return user;
    }

    public Boolean isEmailRegistered(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public Boolean isUsernameRegistered(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
