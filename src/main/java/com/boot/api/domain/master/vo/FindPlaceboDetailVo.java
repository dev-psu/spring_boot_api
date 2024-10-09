package com.boot.api.domain.master.vo;

import com.boot.api.domain.master.entity.Placebo;
import com.boot.api.domain.master.entity.PlaceboLogs;
import com.boot.api.globals.common.enums.Active;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FindPlaceboDetailVo {
    private Integer id;
    private String dateId;
    private String name;
    private String phone;
    private String startDate;
    private String endDate;
    private Active placeboStatus;
    private String note;
    private List<Logs> logList;

    @Data
    public static class Logs {
        private String dateId;
        private String name;
        private String phone;
        private String startDate;
        private String endDate;
        private Active placeboStatus;
        private String note;
        private LocalDateTime createdAt;

        public Logs(PlaceboLogs placeboLogs) {
            this.dateId = placeboLogs.getDateId();
            this.name = placeboLogs.getName();
            this.phone = placeboLogs.getPhone();
            this.startDate = placeboLogs.getStartDate();
            this.endDate = placeboLogs.getEndDate();
            this.placeboStatus = placeboLogs.getPlaceboStatus();
            this.note = placeboLogs.getNote();
            this.createdAt = placeboLogs.getCreatedAt();
        }
    }

    public FindPlaceboDetailVo(Placebo placebo) {
        this.id = placebo.getId();
        this.dateId = placebo.getDateId();
        this.name = placebo.getName();
        this.phone = placebo.getPhone();
        this.startDate = placebo.getStartDate();
        this.endDate = placebo.getEndDate();
        this.placeboStatus = placebo.getPlaceboStatus();
        this.note = placebo.getNote();
    }

    @QueryProjection
    public FindPlaceboDetailVo(Integer id, String dateId, String name, String phone, String startDate, String endDate, Active placeboStatus, String note) {
        this.id = id;
        this.dateId = dateId;
        this.name = name;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeboStatus = placeboStatus;
        this.note = note;
    }
}
