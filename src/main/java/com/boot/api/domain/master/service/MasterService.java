package com.boot.api.domain.master.service;

import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.entity.Placebo;
import com.boot.api.domain.master.repository.PlaceboRepository;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.globals.response.BasePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MasterService {
    private final PlaceboRepository placeboRepository;
    public BasePaginationResponse<FindPlaceboListVo> findPlaceboList(FindPlaceboListDto findPlaceboListDto, Pageable pageable) {
        Page<FindPlaceboListVo> result = placeboRepository.findPlaceboList(findPlaceboListDto, pageable);

        return new BasePaginationResponse<>(result.getContent(), (int) result.getTotalElements());
    }
}
