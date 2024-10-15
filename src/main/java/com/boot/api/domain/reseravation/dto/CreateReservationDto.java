package com.boot.api.domain.reseravation.dto;

import lombok.Data;

@Data
public class CreateReservationDto {
    private String dateId;
    private String timeId;
    private Integer reserveId;
    private String reserveName;
    private String reservePhone;
}
