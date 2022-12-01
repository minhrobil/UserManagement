package com.example.userManagement.service;

import com.example.userManagement.model.User;

import java.util.Optional;

public interface UserService {
    public User login(String email, String password);
    public boolean logout(String email);

    public User addUser(String fullname, String email, String password);
    public User addUserThenAutoActivate(String fullname, String email, String password);

    public boolean activateUser(String activate_code);

    public boolean updatePassword(String email, String password);
    public boolean updateEmail(String email, String newEmail);

    public Optional<User> findByEmail(String email);
    public User findById(String id);
}
