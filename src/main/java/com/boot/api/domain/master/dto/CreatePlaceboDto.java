package com.boot.api.domain.master.dto;

import com.boot.api.globals.common.enums.Active;
import lombok.Data;

@Data
public class CreatePlaceboDto {
    private String dateId;
    private Integer userId;
    private String userName;
    private String phone;
    private String startDate;
    private String endDate;
    private Integer placeboFee;
    private String note;
}
