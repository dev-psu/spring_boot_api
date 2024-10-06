package com.boot.api.domain.master.dto;

import com.boot.api.globals.common.enums.Active;
import lombok.Data;

@Data
public class FindPlaceboListDto {
    private String name;
    private String phone;
    private Active placeboStatus;
}
