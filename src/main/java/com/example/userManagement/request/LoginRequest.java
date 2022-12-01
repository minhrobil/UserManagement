package com.example.userManagement.request;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank(message = "Email cannot blank")
        @Email(message = "Invalid email")
        String email,
        @Size(min =5, max = 20,message = "Password length must between 5 and 20 characters")
        String password
) {
}
