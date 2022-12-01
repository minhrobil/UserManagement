package com.example.userManagement.service;

import com.example.userManagement.exception.UserException;
import com.example.userManagement.hash.Hashing;
import com.example.userManagement.model.State;
import com.example.userManagement.model.User;
import com.example.userManagement.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceInMemory implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Hashing hashing;

    @Override
    public User login(String email, String password) {
        Optional<User> o_user = userRepo.findByEmail(email);
        if(!o_user.isPresent()){
            throw new UserException("User is not found");
        }

        User user = o_user.get();

        //User muon login phai active
        if(user.getState()!= State.ACTIVE){
            throw new UserException("User is not activated");
        }

        if (hashing.validatePassword(password,user.getHashed_password())) {
            return user;
        } else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        return false;
    }

    @Override
    public User addUser(String fullname, String email, String password) {
        return userRepo.addUser(fullname, email, hashing.hashPassword(password));
    }

    @Override
    public User addUserThenAutoActivate(String fullname, String email, String password) {
        return userRepo.addUser(fullname, email, hashing.hashPassword(password), State.ACTIVE);
    }

    @Override
    public boolean activateUser(String activate_code) {
        return false;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return false;
    }

    @Override
    public boolean updateEmail(String email, String newEmail) {
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public User findById(String id) {
        return null;
    }
}
