package com.example.userManagement;

import com.example.userManagement.hash.Hashing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestHash {
    @Autowired
    private Hashing hashing;

    @Test
    public void hashPassword() {
        var passwords = List.of("abc", "quru", "0x-1234", "  ", "$%62123");
        for (String password : passwords) {
            String hashed_password = hashing.hashPassword(password);
            assertThat(hashed_password).isNotNull();
        }
    }

    @Test
    public void validatePassword(){
        var passwords = List.of("abc", "quru", "0x-1234", "  ", "$%62123");
        for (String password : passwords) {
            String hashed_password = hashing.hashPassword(password);
            System.out.println(hashed_password);
            assertThat(hashing.validatePassword(password, hashed_password)).isTrue();
        }

        assertThat(hashing.validatePassword("password", "hashed_password")).isFalse();

    }
}
