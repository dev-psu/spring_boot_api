package com.boot.api.domain.reseravation.entity;

import com.boot.api.domain.reseravation.dto.CreateReservationDto;
import com.boot.api.domain.reseravation.dto.PatchReservationDto;
import com.boot.api.globals.common.enums.Active;
import com.boot.api.globals.common.enums.YesNo;
import com.boot.api.globals.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dateId;
    private String timeId;
    private Integer reserveId;
    private String reserveName;
    private String reservePhone;
    @Enumerated(EnumType.STRING)
    private Active reservationStatus;
    @Enumerated(EnumType.STRING)
    private YesNo isDeletedYn;

    public Reservation(CreateReservationDto createReservationDto) {
        this.dateId = createReservationDto.getDateId();
        this.timeId = createReservationDto.getTimeId();
        this.reserveId = createReservationDto.getReserveId();
        this.reserveName = createReservationDto.getReserveName();
        this.reservePhone = createReservationDto.getReservePhone();
        this.reservationStatus = Active.ACTIVE;
        this.isDeletedYn = YesNo.N;
    }

    public void patchReservation(PatchReservationDto patchReservationDto) {
        this.dateId = patchReservationDto.getDateId();
        this.timeId = patchReservationDto.getTimeId();
        this.reserveId = patchReservationDto.getReserveId();
        this.reserveName = patchReservationDto.getReserveName();
        this.reservePhone = patchReservationDto.getReservePhone();
        this.reservationStatus = Active.ACTIVE;
        this.isDeletedYn = YesNo.N;
    }

    public void inActive() {
        this.reservationStatus = Active.INACTIVE;
    }

    public void isDeleted() {
        this.reservationStatus = Active.DELETED;
        this.isDeletedYn = YesNo.Y;
    }
}
