package com.boot.api.domain.reseravation.entity;

import com.boot.api.globals.common.enums.Active;
import com.boot.api.globals.common.enums.YesNo;
import com.boot.api.globals.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReservationLogs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer reservationId;
    private String dateId;
    private String timeId;
    private Integer reserveId;
    private String reserveName;
    private String reservePhone;
    @Enumerated(EnumType.STRING)
    private Active reservationStatus;
    @Enumerated(EnumType.STRING)
    private YesNo isDeletedYn;

    public ReservationLogs(Reservation reservation) {
        this.reservationId = reservation.getReserveId();
        this.dateId = reservation.getDateId();
        this.timeId = reservation.getTimeId();
        this.reserveId = reservation.getReserveId();
        this.reserveName = reservation.getReserveName();
        this.reservePhone = reservation.getReservePhone();
        this.reservationStatus = reservation.getReservationStatus();
        this.isDeletedYn = reservation.getIsDeletedYn();
    }
}