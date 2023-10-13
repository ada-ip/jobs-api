package com.adaip.jobsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Username")
    @NotNull(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long.")
    @Pattern(regexp = "^[a-z0-9\\-_]+$", message = "Username can only contain lowercase letters, digits, hyphens, and underscores.")
    private String username;

    @Column(name = "Email")
    @NotNull(message = "Email is required.")
    @Size(max = 320, message = "Email must have less than 320 characters long.")
    @Email(message = "Invalid email format.")
    private String email;

    @Column(name = "Password")
    @NotNull(message = "Password is required.")
    @Size(min = 7, message = "Password must be at least 7 characters long.")
    private String password;

    public User() {
        // Default constructor
    }

    public User(String username, String email, String password) {
        this.username = username.trim();
        this.email = email.trim();
        this.password = password.trim();
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
