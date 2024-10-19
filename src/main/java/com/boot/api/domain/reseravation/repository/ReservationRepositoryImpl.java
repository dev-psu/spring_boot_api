package com.boot.api.domain.reseravation.repository;

import com.boot.api.domain.reseravation.dto.FindReservationListDto;
import com.boot.api.domain.reseravation.vo.FindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.FindReservationListVo;
import com.boot.api.domain.reseravation.vo.QFindReservationDetailVo;
import com.boot.api.domain.reseravation.vo.QFindReservationListVo;
import com.boot.api.globals.common.enums.Active;
import com.boot.api.globals.common.enums.YesNo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.boot.api.domain.reseravation.entity.QReservation.reservation;
import static com.boot.api.globals.common.enums.YesNo.Y;
import static io.jsonwebtoken.lang.Strings.hasText;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindReservationListVo> findReservationList(FindReservationListDto findReservationListDto, Pageable pageable) {
        List<FindReservationListVo> items = queryFactory.select(new QFindReservationListVo(
            reservation.id,
            reservation.dateId,
            reservation.timeId,
            reservation.reserveId,
            reservation.reserveName,
            reservation.reservePhone,
            reservation.reservationStatus))
        .from(reservation)
        .where(dateIdBetween(findReservationListDto.getDateIdBetweenFrom(), findReservationListDto.getDateIdBetweenTo()),
            reserveIdEq(findReservationListDto.getReserveId()),
            reserveNameEq(findReservationListDto.getReserveName()),
            reservePhoneEq(findReservationListDto.getReservePhone()),
            reservationStatusEq(findReservationListDto.getReservationStatus()),
            reservation.isDeletedYn.ne(Y))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

        Long count = queryFactory.select(reservation.count())
            .from(reservation)
            .where(dateIdBetween(findReservationListDto.getDateIdBetweenFrom(), findReservationListDto.getDateIdBetweenTo()),
                reserveIdEq(findReservationListDto.getReserveId()),
                reserveNameEq(findReservationListDto.getReserveName()),
                reservePhoneEq(findReservationListDto.getReservePhone()),
                reservationStatusEq(findReservationListDto.getReservationStatus()),
                reservation.isDeletedYn.ne(Y))
            .fetchOne();

        return new PageImpl<>(items, pageable, count);
    }

    @Override
    public FindReservationDetailVo findReservationDetail(Integer id) {
        return queryFactory.select(new QFindReservationDetailVo(
            reservation.id,
            reservation.dateId,
            reservation.timeId,
            reservation.reserveId,
            reservation.reserveName,
            reservation.reservePhone,
            reservation.reservationStatus))
        .from(reservation)
        .where(reservation.id.eq(id),
            reservation.reservationStatus.ne(Active.DELETED))
        .fetchOne();
    }

    private BooleanExpression dateIdBetween(String dateIdBetweenFrom, String dateIdBetweenTo) {
        return hasText(dateIdBetweenFrom) && hasText(dateIdBetweenTo)
            ? reservation.dateId.between(dateIdBetweenFrom, dateIdBetweenTo) : null;
    }

    private BooleanExpression reserveIdEq(Integer reserveId) {
        return hasText(reserveId.toString()) ? reservation.reserveId.eq(reserveId) : null;
    }

    private BooleanExpression reserveNameEq(String reserveName) {
        return hasText(reserveName) ? reservation.reserveName.eq(reserveName) : null;
    }

    private BooleanExpression reservePhoneEq(String reservePhone) {
        return hasText(reservePhone) ? reservation.reservePhone.eq(reservePhone) : null;
    }

    private BooleanExpression reservationStatusEq(Active reservationStatus) {
        return hasText(reservationStatus.getValue()) ? reservation.reservationStatus.eq(reservationStatus) : null;
    }
}
