package com.boot.api.domain.master.vo;

import com.boot.api.globals.common.enums.Active;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindPlaceboListVo {
    private Integer id;
    private String dateId;
    private String name;
    private String phone;
    private String startDate;
    private String endDate;
    private Active placeboStatus;
    private String note;

    @QueryProjection
    public FindPlaceboListVo(Integer id, String dateId, String name, String phone, String startDate, String endDate, Active placeboStatus, String note) {
        this.id = id;
        this.dateId = dateId;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeboStatus = placeboStatus;
        this.note = note;
    }
}
