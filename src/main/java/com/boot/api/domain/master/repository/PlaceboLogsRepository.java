package com.boot.api.domain.master.repository;

import com.boot.api.domain.master.entity.PlaceboLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceboLogsRepository extends JpaRepository<PlaceboLogs, Integer> {
    List<PlaceboLogs> findPlaceboLogsByPlaceboId(Integer placeboId);
}
