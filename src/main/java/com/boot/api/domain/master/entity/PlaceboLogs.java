package com.boot.api.domain.master.entity;

import com.boot.api.globals.common.enums.Active;
import com.boot.api.globals.common.enums.YesNo;
import com.boot.api.globals.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

@Entity
@Getter
@NoArgsConstructor
public class PlaceboLogs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer placeboId;
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

    public PlaceboLogs(Placebo placebo) {
        this.placeboId = placebo.getId();
        this.dateId = placebo.getDateId();
        this.name = placebo.getName();
        this.phone = placebo.getPhone();
        this.startDate = placebo.getStartDate();
        this.endDate = placebo.getEndDate();
        this.placeboFee = placebo.getPlaceboFee();
        this.placeboStatus = placebo.getPlaceboStatus();
        this.note = placebo.getNote();
        this.isDeletedYn = placebo.getIsDeletedYn();
    }
}
