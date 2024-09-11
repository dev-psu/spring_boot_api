package com.boot.api.globals.common.enums;

import com.boot.api.globals.error.exception.EntityNotFoundException;
import lombok.Getter;
import org.antlr.v4.runtime.Token;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Getter
public enum TokenType implements BaseEnum {
    ACCESS("ACCESS", "ACCESS_TOKEN"),
    REFRESH("REFRESH", "REFRESH_TOKEN");

    private final String value;
    private final String desc;

    private final static Map<String, TokenType> TYPES = unmodifiableMap(
        stream(values()).collect(toMap(TokenType::getValue, identity())));

    TokenType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static TokenType find(String value) {
        if(TYPES.containsKey(value)) {
            return TYPES.get(value);
        }

        throw new EntityNotFoundException(value, ErrorCode.ENTITY_NOT_FOUND);
    }
}
