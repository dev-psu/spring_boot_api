package com.boot.api.domain.user.dto;

import com.boot.api.globals.annotations.validator.ValidPassword;
import com.boot.api.globals.annotations.validator.ValidPhone;
import com.boot.api.globals.common.enums.Role;
import com.boot.api.globals.common.enums.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CreateUserDto {
    private String userEmail;
    private String userName;
    @ValidPassword
    private String userPassword;
    @ValidPassword
    private String userPasswordCheck;
    @ValidPhone
    private String phone;
    private Integer groupId;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    private Role role;
}
