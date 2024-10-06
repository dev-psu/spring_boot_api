package com.boot.api.globals.common.enums;

import com.boot.api.globals.error.exception.EntityNotFoundException;
import lombok.Getter;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Getter
public enum YesNo implements BaseEnum {
    Y("Y", "Y"),
    N("N", "N");

    private final String value;
    private final String desc;

    private final static Map<String, YesNo> TYPES = unmodifiableMap(
        stream(values()).collect(toMap(YesNo::getValue, identity())));

    YesNo(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static YesNo find(String value) {
        if (TYPES.containsKey(value)) {
            return TYPES.get(value);
        }

        throw new EntityNotFoundException(value, ErrorCode.ENTITY_NOT_FOUND);
    }
}
