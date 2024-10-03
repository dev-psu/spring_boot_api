package com.boot.api.domain.user.vo;

import com.boot.api.globals.common.enums.Role;
import com.boot.api.globals.common.enums.UserStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindUserListResultVo {
    private Integer id;
    private String userName;
    private String userEmail;
    private String phone;
    private UserStatus userStatus;
    private Role role;

    @QueryProjection
    public FindUserListResultVo(Integer id, String userName, String userEmail, String phone, UserStatus userStatus, Role role) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phone = phone;
        this.userStatus = userStatus;
        this.role = role;
    }
}
