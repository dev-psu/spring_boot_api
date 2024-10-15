package com.boot.api.domain.reseravation.vo;

import com.boot.api.globals.common.enums.Active;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FindReservationDetailVo {
    private Integer id;
    private String dateId;
    private String timeId;
    private Integer reserveId;
    private String reserveName;
    private String reservePhone;
    private Active ReservationStatus;

    @QueryProjection
    public FindReservationDetailVo(Integer id, String dateId, String timeId, Integer reserveId, String reserveName, String reservePhone, Active reservationStatus) {
        this.id = id;
        this.dateId = dateId;
        this.timeId = timeId;
        this.reserveId = reserveId;
        this.reserveName = reserveName;
        this.reservePhone = reservePhone;
        ReservationStatus = reservationStatus;
    }
}
