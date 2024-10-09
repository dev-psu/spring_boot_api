package com.boot.api.domain.master.service;

import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.entity.Placebo;
import com.boot.api.domain.master.entity.PlaceboLogs;
import com.boot.api.domain.master.repository.PlaceboLogsRepository;
import com.boot.api.domain.master.repository.PlaceboRepository;
import com.boot.api.domain.master.vo.FindPlaceboDetailVo;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import com.boot.api.globals.response.BasePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MasterService {
    private final PlaceboRepository placeboRepository;
    private final PlaceboLogsRepository placeboLogsRepository;
    public BasePaginationResponse<FindPlaceboListVo> findPlaceboList(FindPlaceboListDto findPlaceboListDto, Pageable pageable) {
        Page<FindPlaceboListVo> result = placeboRepository.findPlaceboList(findPlaceboListDto, pageable);

        return new BasePaginationResponse<>(result.getContent(), (int) result.getTotalElements());
    }

    public FindPlaceboDetailVo findPlaceboDetail(Integer id) {
        Placebo placebo = placeboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.ENTITY_NOT_FOUND));
        List<PlaceboLogs> placeboLogs = placeboLogsRepository.findPlaceboLogsByPlaceboId(placebo.getId());

        FindPlaceboDetailVo findPlaceboDetailVo = new FindPlaceboDetailVo(placebo);
        List<FindPlaceboDetailVo.Logs> logs = placeboLogs.stream()
            .map(FindPlaceboDetailVo.Logs::new)
            .sorted(Comparator.comparing(FindPlaceboDetailVo.Logs::getCreatedAt).reversed())
            .skip(1)
            .collect(Collectors.toList());

        findPlaceboDetailVo.setLogList(logs);

        return findPlaceboDetailVo;
    }
}
