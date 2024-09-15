package com.boot.api.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
public class LoginVo {
    private String accessToken;
    @JsonIgnore
    private String refreshToken;

    @Builder
    public LoginVo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
