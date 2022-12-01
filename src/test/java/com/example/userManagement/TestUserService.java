package com.example.userManagement;

import com.example.userManagement.exception.UserException;
import com.example.userManagement.model.User;
import com.example.userManagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TestUserService {
    @Autowired private UserService userService;

    @Test
    public void addUser(){
        User user = userService.addUser("QUang Minh","minh@gmail.com","123456");
        assertThat(user).isNotNull();
    }

    @Test
    public void login_when_account_is_pending(){
        userService.addUser("QUang Minh","minh@gmail.com","123456");
        assertThatThrownBy(()->{
            userService.login("minh@gmail.com", "123456");
        }).isInstanceOf(UserException.class).hasMessageContaining("User is not activated");
    }

    @Test
    public void login_when_account_is_not_found(){
        assertThatThrownBy(()->{
            userService.login("minh123@gmail.com", "123456");
        }).isInstanceOf(UserException.class).hasMessageContaining("User is not found");
    }

    @Test
    public void login_when_account_is_incorrect(){
        userService.addUserThenAutoActivate("QUang Minh","minh@gmail.com","123456");
        assertThatThrownBy(()->{
            userService.login("minh@gmail.com", "123123456");
        }).isInstanceOf(UserException.class).hasMessageContaining("Password is incorrect");
    }

    @Test
    public void login_success(){
        userService.addUserThenAutoActivate("QUang Minh","minh@gmail.com","123456");
        User user = userService.login("minh@gmail.com", "123456");
        assertThat(user).isNotNull();
    }
}
