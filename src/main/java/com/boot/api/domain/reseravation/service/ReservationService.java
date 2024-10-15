package com.boot.api.domain.reseravation.service;

import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.repository.ReservationRepository;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.globals.response.BasePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public BasePaginationResponse<FindReservationListVo> findReservationList(FindReservationListDto findReservationListDto, Pageable pageable) {
        Page<FindReservationListVo> result = reservationRepository.findReservationList(findReservationListDto, pageable);
        return new BasePaginationResponse<>(result.getContent(), (int) result.getTotalElements());
    }

    public FindReservationDetailVo findReservationDetail(Integer id) {
        return reservationRepository.findReservationDetail(id);
    }
}
