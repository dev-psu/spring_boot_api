package com.boot.api.domain.user.dto;

import lombok.Data;

@Data
public class FindUserListDto {
    private String userEmail;
    private String phone;
}
