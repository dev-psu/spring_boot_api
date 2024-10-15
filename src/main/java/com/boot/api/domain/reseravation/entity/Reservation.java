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
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dateId;
    private String timeId;
    private Integer reserverId;
    private String reserverName;
    private String reserverPhone;
    @Enumerated(EnumType.STRING)
    private Active reservationStatus;
    @Enumerated(EnumType.STRING)
    private YesNo isDeletedYn;
}
