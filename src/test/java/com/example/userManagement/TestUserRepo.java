package com.example.userManagement;

import com.example.userManagement.model.State;
import com.example.userManagement.model.User;
import com.example.userManagement.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

//@SpringBootTest
public class TestUserRepo {
    @Test
    public void addUser() {
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("John Levy", "levy@gmail.com", "OX-1234", State.PENDING);
        assertThat(user).isNotNull();
        System.out.println(user.getId());
        assertThat(user.getId()).isNotBlank();
    }

    @Test
    public void addUserWithPendingState() {
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("John Levy", "levy@gmail.com", "OX-1234");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotBlank();
        assertThat(user.getState()).isEqualTo(State.PENDING);
    }

    @Test
    public void isEmailExist(){
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("John Levy", "levy@gmail.com", "OX-1234");
        userRepo.addUser("Quang Minh", "quangminh@gmail.com", "OX-12345");
        userRepo.addUser("Mai Tran", "maitran@gmail.com", "OX-123456");

        assertThat(userRepo.isEmailExist("levy@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("quangminH@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("maitran@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("maitran1@gmail.com")).isFalse();
    }

    @Test
    public void findByEmail(){
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("John Levy", "levy@gmail.com", "OX-1234");
        userRepo.addUser("Quang Minh", "quangminh@gmail.com", "OX-12345");
        userRepo.addUser("Mai Tran", "maitran@gmail.com", "OX-123456");

        assertThat(userRepo.findByEmail("levy@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("quangminH@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("maitran@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("maitran1@gmail.com")).isNotPresent();
    }
}
