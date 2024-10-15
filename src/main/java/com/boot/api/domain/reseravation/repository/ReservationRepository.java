package com.boot.api.domain.reseravation.repository;

import com.boot.api.domain.reseravation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, ReservationRepositoryCustom {
}
