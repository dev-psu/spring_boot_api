package com.boot.api.globals.session;

import com.boot.api.globals.common.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
public class UserSessionInfo {
    private Integer id;
    private String userEmail;
    private Role role;

    @Builder
    public UserSessionInfo(Integer id, String userEmail, Role role) {
        this.id = id;
        this.userEmail = userEmail;
        this.role = role;
    }
}
