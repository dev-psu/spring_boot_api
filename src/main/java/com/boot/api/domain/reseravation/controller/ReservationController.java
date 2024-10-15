package com.boot.api.domain.reseravation.controller;

import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.repository.ReservationRepository;
import com.boot.api.domain.reseravation.service.ReservationService;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
import com.boot.api.globals.annotations.pagination.Pagination;
import com.boot.api.globals.response.BasePaginationResponse;
import com.boot.api.globals.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
