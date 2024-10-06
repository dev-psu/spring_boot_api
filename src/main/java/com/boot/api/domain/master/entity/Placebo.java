package com.boot.api.domain.master.entity;

import com.boot.api.globals.common.enums.Active;
import com.boot.api.globals.common.enums.YesNo;
import com.boot.api.globals.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Placebo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dateId;
    private String name;
    private String phone;
    private String startDate;
    private String endDate;
    private Integer placeboFee;
    @Enumerated(EnumType.STRING)
    private Active placeboStatus;
    private String note;
    @Enumerated(EnumType.STRING)
    private YesNo isDeletedYn;

    public Placebo(Integer id, String dateId, String name, String phone, String startDate, String endDate, Integer placeboFee, Active placeboStatus, String note, YesNo isDeletedYn) {
        this.id = id;
        this.dateId = dateId;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeboFee = placeboFee;
        this.placeboStatus = placeboStatus;
        this.note = note;
        this.isDeletedYn = isDeletedYn;
    }

    public void inActive() { this.placeboStatus = Active.INACTIVE; }
    public void deleted() { this.isDeletedYn = YesNo.Y; }
}
