package com.boot.api.domain.master.controller;

import com.boot.api.domain.master.dto.CreatePlaceboDto;
import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.dto.PatchPlaceboDto;
import com.boot.api.domain.master.service.MasterService;
import com.boot.api.domain.master.vo.FindPlaceboDetailVo;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.globals.annotations.pagination.Pagination;
import com.boot.api.globals.response.BasePaginationResponse;
import com.boot.api.globals.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 위약자 상세 조회
     * @param id
     * @return BaseResponse<FindPlaceboDetailVo>
     */
    @GetMapping("/master/placebo/{id}")
    public ResponseEntity<BaseResponse<FindPlaceboDetailVo>> findPlaceboDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(BaseResponse.successWithData(masterService.findPlaceboDetail(id)));
    }

    /**
     * 위약 등록
     * @param createPlaceboDto
     * @return
     */
    @PostMapping("/master/placebo")
    public ResponseEntity<BaseResponse<Void>> createPlacebo(@RequestBody @Valid CreatePlaceboDto createPlaceboDto) {
        masterService.createPlacebo(createPlaceboDto);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 위약자 수정
     * @param id
     * @param patchPlaceboDto
     * @return
     */
    @PatchMapping("/master/placebo/{id}")
    public ResponseEntity<BaseResponse<Void>> patchPlacebo(@PathVariable("id") Integer id, @RequestBody @Valid PatchPlaceboDto patchPlaceboDto) {
        masterService.patchPlacebo(id, patchPlaceboDto);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 위약 비활성화
     * @param id
     * @return BaseResponse<>
     */
    @PostMapping("/master/placebo/{id}")
    public ResponseEntity<BaseResponse<Void>> inActivePlacebo(@PathVariable("id") Integer id) {
        masterService.inActivePlacebo(id);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 위약 삭제
     * @param id
     * @return BaseResponse<>
     */
    @DeleteMapping("/master/placebo/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePlcaebo(@PathVariable("id") Integer id) {
        masterService.deletePlacebo(id);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }
}
