package com.boot.api.domain.reseravation.dto;

import com.boot.api.globals.common.enums.Active;
import lombok.Data;

@Data
public class FindReservationListDto {
    private String dateIdBetweenFrom;
    private String dateIdBetweenTo;
    private Integer reserveId;
    private String reserveName;
    private String reservePhone;
    private Active reservationStatus;
}
