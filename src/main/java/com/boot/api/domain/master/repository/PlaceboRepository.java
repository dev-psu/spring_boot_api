package com.boot.api.domain.master.repository;

import com.boot.api.domain.master.entity.Placebo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceboRepository extends JpaRepository<Placebo, Integer>, PlaceboRepositoryCustom {
}
