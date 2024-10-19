package com.boot.api.domain.master.dto;

import lombok.Data;

@Data
public class PatchPlaceboDto {
    private String dateId;
    private Integer userId;
    private String userName;
    private String phone;
    private String startDate;
    private String endDate;
    private Integer placeboFee;
    private String note;
}
