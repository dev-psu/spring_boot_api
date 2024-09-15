package com.boot.api.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    @Email
    private String userEmail;
    @NotNull
    private String userPassword;
}
