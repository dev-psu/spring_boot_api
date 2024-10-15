package com.boot.api.domain.reseravation.service;

import com.boot.api.domain.reseravation.dto.CreateReservationDto;
import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.dto.PatchReservationDto;
import com.boot.api.domain.reseravation.entity.Reservation;
import com.boot.api.domain.reseravation.entity.ReservationLogs;
import com.boot.api.domain.reseravation.repository.ReservationLogsRepository;
import com.boot.api.domain.reseravation.repository.ReservationRepository;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
import com.boot.api.domain.user.dto.FindUserDto;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.domain.user.vo.FindUserVo;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import com.boot.api.globals.response.BasePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationLogsRepository reservationLogsRepository;
    private final UserRepository userRepository;

    public BasePaginationResponse<FindReservationListVo> findReservationList(FindReservationListDto findReservationListDto, Pageable pageable) {
        Page<FindReservationListVo> result = reservationRepository.findReservationList(findReservationListDto, pageable);
        return new BasePaginationResponse<>(result.getContent(), (int) result.getTotalElements());
    }

    public FindReservationDetailVo findReservationDetail(Integer id) {
        return reservationRepository.findReservationDetail(id);
    }

    @Transactional
    public void createReservation(CreateReservationDto createReservationDto) {
        List<FindUserVo> findUserList = userRepository.findUser(new FindUserDto(createReservationDto.getReserveName(), createReservationDto.getReservePhone()));

        if(findUserList.size() == 1) {
            createReservationDto.setReserveId(findUserList.get(0).getId());
        }

        Reservation reservation = new Reservation(createReservationDto);
        reservationRepository.save(reservation);

        reservationLogsRepository.save(new ReservationLogs(reservation));
    }

    @Transactional
    public void patchReservation(Integer id, PatchReservationDto patchReservationDto) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.RESERVATION_NOT_FOUNT));

        if(!(reservation.getReserveName().equals(patchReservationDto.getReserveName())
            && reservation.getReservePhone().equals(patchReservationDto.getReservePhone()))) {
            List<FindUserVo> findUserList = userRepository.findUser(new FindUserDto(patchReservationDto.getReserveName(), patchReservationDto.getReservePhone()));

            if(findUserList.size() == 1) {
                patchReservationDto.setReserveId(findUserList.get(0).getId());
            }
        }

        reservation.patchReservation(patchReservationDto);

        reservationLogsRepository.save(new ReservationLogs(reservation));
    }

    @Transactional
    public void inActiveReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.RESERVATION_NOT_FOUNT));

        reservation.inActive();
    }

    @Transactional
    public void deleteReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.RESERVATION_NOT_FOUNT));

        reservation.isDeleted();
    }
}
