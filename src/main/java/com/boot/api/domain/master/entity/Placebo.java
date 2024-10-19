package com.boot.api.domain.master.entity;

import com.boot.api.domain.master.dto.CreatePlaceboDto;
import com.boot.api.domain.master.dto.PatchPlaceboDto;
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
    private Integer userId;
    private String userName;
    private String phone;
    private String startDate;
    private String endDate;
    private Integer placeboFee;
    @Enumerated(EnumType.STRING)
    private Active placeboStatus;
    private String note;
    @Enumerated(EnumType.STRING)
    private YesNo isDeletedYn;

    public Placebo(CreatePlaceboDto createPlaceboDto) {
        this.dateId = createPlaceboDto.getDateId();
        this.userId = createPlaceboDto.getUserId();
        this.userName = createPlaceboDto.getUserName();
        this.phone = createPlaceboDto.getPhone();
        this.startDate = createPlaceboDto.getStartDate();
        this.endDate = createPlaceboDto.getEndDate();
        this.placeboFee = createPlaceboDto.getPlaceboFee();
        this.placeboStatus = Active.ACTIVE;
        this.note = createPlaceboDto.getNote();
        this.isDeletedYn = YesNo.N;
    }

    public void patchPlacebo(PatchPlaceboDto patchPlaceboDto) {
        this.dateId = patchPlaceboDto.getDateId();
        this.userId = patchPlaceboDto.getUserId();
        this.userName = patchPlaceboDto.getUserName();
        this.phone = patchPlaceboDto.getPhone();
        this.startDate = patchPlaceboDto.getStartDate();
        this.endDate = patchPlaceboDto.getEndDate();
        this.placeboFee = patchPlaceboDto.getPlaceboFee();
        this.placeboStatus = Active.ACTIVE;
        this.note = patchPlaceboDto.getNote();
        this.isDeletedYn = YesNo.N;
    }

    public void inActive() { this.placeboStatus = Active.INACTIVE; }
    public void deleted() { this.isDeletedYn = YesNo.Y; }
}
