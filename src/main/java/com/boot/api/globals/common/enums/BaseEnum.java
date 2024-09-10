package com.boot.api.globals.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum {
    @JsonValue
    String getValue();
    String getDesc();
}
