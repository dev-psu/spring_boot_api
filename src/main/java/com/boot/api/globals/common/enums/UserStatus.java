package com.boot.api.globals.common.enums;

import com.boot.api.globals.error.exception.EntityNotFoundException;
import lombok.Getter;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Getter
public enum UserStatus implements BaseEnum {
    ACTIVE("ACTIVE", "ACTIVE"),
    INACTIVE("INACTIVE", "INACTIVE"),
    BLOCKED("BLOCKED", "BLOCKED"),
    DELETED("DELETED", "DELETED");

    private final String value;
    private final String desc;

    private final static Map<String, UserStatus> TYPES = unmodifiableMap(
        stream(values()).collect(toMap(UserStatus::getValue, identity())));

    UserStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserStatus find(String value) {
        if (TYPES.containsKey(value)) {
            return TYPES.get(value);
        }

        throw new EntityNotFoundException(value, ErrorCode.ENTITY_NOT_FOUND);
    }
}
