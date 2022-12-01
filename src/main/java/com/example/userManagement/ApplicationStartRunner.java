package com.example.userManagement;

import com.example.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartRunner implements ApplicationRunner {
    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.addUserThenAutoActivate("Quang Minh","minh@gmail.com", "123456");
        userService.addUser("Quang Minh","minh1@gmail.com", "123456");
    }
}
