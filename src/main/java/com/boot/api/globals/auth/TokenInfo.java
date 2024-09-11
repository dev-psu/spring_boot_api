package com.boot.api.globals.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TokenInfo {
    private String grantType;
    private String accessToken;
    @JsonIgnore
    private String refreshToken;

    public TokenInfo(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
