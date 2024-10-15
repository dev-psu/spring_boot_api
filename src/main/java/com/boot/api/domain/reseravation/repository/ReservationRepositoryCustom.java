package com.boot.api.domain.reseravation.repository;

import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {
    Page<FindReservationListVo> findReservationList(FindReservationListDto findReservationListDto, Pageable pageable);
    FindReservationDetailVo findReservationDetail(Integer id);
}
