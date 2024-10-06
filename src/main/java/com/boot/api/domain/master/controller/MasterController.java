package com.boot.api.domain.master.controller;

import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.service.MasterService;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.globals.annotations.pagination.Pagination;
import com.boot.api.globals.response.BasePaginationResponse;
import com.boot.api.globals.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MasterController {
    private final MasterService masterService;

    /**
     * 위약자 리스트 조회
     * @param findPlaceboListDto
     * @param pageable
     * @return BaseResponse<BasePaginationResponse<FindPlaceboListVo>>
     */
    @GetMapping("/master/placebo")
    public ResponseEntity<BaseResponse<BasePaginationResponse<FindPlaceboListVo>>> findPlaceboList(FindPlaceboListDto findPlaceboListDto, @Pagination Pageable pageable) {
        return ResponseEntity.ok(BaseResponse.successWithData(masterService.findPlaceboList(findPlaceboListDto, pageable)));
    }
}
