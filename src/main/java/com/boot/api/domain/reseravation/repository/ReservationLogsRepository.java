package com.boot.api.domain.reseravation.repository;

import com.boot.api.domain.reseravation.entity.ReservationLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationLogsRepository extends JpaRepository<ReservationLogs, Integer> {
}
