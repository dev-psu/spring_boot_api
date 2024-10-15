package com.boot.api.domain.reseravation.controller;

import com.boot.api.domain.reseravation.dto.CreateReservationDto;
import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.dto.PatchReservationDto;
import com.boot.api.domain.reseravation.repository.ReservationRepository;
import com.boot.api.domain.reseravation.service.ReservationService;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
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
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * 예약 리스트 조회
     * @param findReservationListDto
     * @param pageable
     * @return BaseResponse<BasePaginationResponse<FindReservationListVo>>
     */
    @GetMapping("/reservation")
    public ResponseEntity<BaseResponse<BasePaginationResponse<FindReservationListVo>>> findReservationList(FindReservationListDto findReservationListDto, @Pagination Pageable pageable) {

        return ResponseEntity.ok(BaseResponse.successWithData(reservationService.findReservationList(findReservationListDto, pageable)));
    }

    /**
     * 예약 상세 조회
     * @param id
     * @return BaseResponse<FindReservationDetailVo>
     */
    @GetMapping("/reservation/{id}")
    public ResponseEntity<BaseResponse<FindReservationDetailVo>> findReservationDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(BaseResponse.successWithData(reservationService.findReservationDetail(id)));
    }

    /**
     * 예약 생성
     * @param createReservationDto
     * @return BaseResponse<>
     */
    @PostMapping("/reservation")
    public ResponseEntity<BaseResponse<Void>> createReservation(@RequestBody @Valid CreateReservationDto createReservationDto) {
        reservationService.createReservation(createReservationDto);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 예약 수정
     * @param id
     * @param patchReservationDto
     * @return BaseResponse<>
     */

    @PatchMapping("/reservation/{id}")
    public ResponseEntity<BaseResponse<Void>> patchReservation(@PathVariable("id") Integer id, PatchReservationDto patchReservationDto) {
        reservationService.patchReservation(id, patchReservationDto);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 예약 비활성화
     * @param id
     * @return BaseResponse<>
     */
    @PostMapping("/reservation/{id}")
    public ResponseEntity<BaseResponse<Void>> inActiveReservation(@PathVariable("id") Integer id) {
        reservationService.inActiveReservation(id);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

    /**
     * 예약 삭제
     * @param id
     * @return BaseResponse
     */
    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteReservation(@PathVariable("id") Integer id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.ok(BaseResponse.successWithNoData());
    }

}
