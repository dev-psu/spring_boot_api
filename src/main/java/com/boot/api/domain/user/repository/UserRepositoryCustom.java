package com.boot.api.domain.user.repository;

import com.boot.api.domain.user.dto.FindUserDto;
import com.boot.api.domain.user.dto.FindUserListDto;
import com.boot.api.domain.user.vo.FindUserListResultVo;
import com.boot.api.domain.user.vo.FindUserVo;
import com.boot.api.globals.session.UserSessionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    Page<FindUserListResultVo> findUserList(FindUserListDto findUserListDto, UserSessionInfo userSessionInfo, Pageable pageable);
    List<FindUserVo> findUser(FindUserDto findUserDto);
}
