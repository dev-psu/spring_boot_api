package com.boot.api.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FindUserDto {
    private String userName;
    private String phone;

    @Builder
    public FindUserDto(String userName, String phone) {
        this.userName = userName;
        this.phone = phone;
    }
}
