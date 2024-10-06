package com.boot.api.domain.master.repository;

import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceboRepositoryCustom {
    Page<FindPlaceboListVo> findPlaceboList(FindPlaceboListDto findPlaceboListDto, Pageable pageable);
}
