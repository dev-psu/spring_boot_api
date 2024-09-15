package com.boot.api.globals.session;

import lombok.Builder;
import lombok.Data;

@Data
public class UserSessionInfo {
    private Integer id;
    private String userEmail;

    @Builder
    public UserSessionInfo(Integer id, String userEmail) {
        this.id = id;
        this.userEmail = userEmail;
    }
}
