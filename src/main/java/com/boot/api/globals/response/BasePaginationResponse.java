package com.boot.api.globals.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BasePaginationResponse<T> {
    private List<T> items;
    private int totalCount;

    public BasePaginationResponse(List<T> items, int totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }
}
