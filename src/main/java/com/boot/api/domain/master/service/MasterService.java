package com.boot.api.domain.master.service;

import com.boot.api.domain.master.dto.CreatePlaceboDto;
import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.dto.PatchPlaceboDto;
import com.boot.api.domain.master.entity.Placebo;
import com.boot.api.domain.master.entity.PlaceboLogs;
import com.boot.api.domain.master.repository.PlaceboLogsRepository;
import com.boot.api.domain.master.repository.PlaceboRepository;
import com.boot.api.domain.master.vo.FindPlaceboDetailVo;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.domain.user.dto.FindUserDto;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.domain.user.vo.FindUserVo;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import com.boot.api.globals.response.BasePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
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
    private final UserRepository userRepository;

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

    public void createPlacebo(CreatePlaceboDto createPlaceboDto) {
        List<FindUserVo> findUserList = userRepository.findUser(new FindUserDto(createPlaceboDto.getUserName(), createPlaceboDto.getPhone()));

        if(findUserList.size() == 1) {
            createPlaceboDto.setUserId(findUserList.get(0).getId());
        }

        Placebo placebo = new Placebo(createPlaceboDto);

        placeboRepository.save(placebo);
        placeboLogsRepository.save(new PlaceboLogs(placebo));
    }

    public void patchPlacebo(Integer id, PatchPlaceboDto patchPlaceboDto) {
        Placebo placebo = placeboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.ENTITY_NOT_FOUND));

        List<FindUserVo> findUserList = userRepository.findUser(new FindUserDto(patchPlaceboDto.getUserName(), patchPlaceboDto.getPhone()));

        if(findUserList.size() == 1) {
            patchPlaceboDto.setUserId(findUserList.get(0).getId());
        }

        placebo.patchPlacebo(patchPlaceboDto);

        placeboLogsRepository.save(new PlaceboLogs(placebo));
    }

    public void inActivePlacebo(Integer id) {
        Placebo placebo = placeboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.ENTITY_NOT_FOUND));

        placebo.inActive();

        placeboLogsRepository.save(new PlaceboLogs(placebo));
    }

    public void deletePlacebo(Integer id) {
        Placebo placebo = placeboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.ENTITY_NOT_FOUND));

        placebo.deleted();

        placeboLogsRepository.save(new PlaceboLogs(placebo));
    }
}
