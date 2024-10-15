package com.boot.api.domain.user.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindUserVo {
    private Integer id;
    private String userName;
    private String phone;

    @QueryProjection
    public FindUserVo(Integer id, String userName, String phone) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
    }
}
