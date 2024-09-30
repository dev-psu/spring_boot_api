package com.boot.api.domain.user.service;

import com.boot.api.domain.user.dto.FindUserListDto;
import com.boot.api.domain.user.dto.LoginDto;
import com.boot.api.domain.user.entity.User;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.domain.user.vo.FindUserListResultVo;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.common.enums.UserStatus;
import com.boot.api.globals.error.exception.BusinessException;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import com.boot.api.globals.response.BasePaginationResponse;
import com.boot.api.globals.session.UserSessionInfo;
import com.boot.api.globals.utils.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public BasePaginationResponse<FindUserListResultVo> findUserList(FindUserListDto findUserListDto, UserSessionInfo userSessionInfo, Pageable pageable) {
        Page<FindUserListResultVo> result = userRepository.findUserList(findUserListDto, userSessionInfo, pageable);

        return new BasePaginationResponse<>(result.getContent(), (int) result.getTotalElements());
    }

    public User findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.USER_NOT_FOUND));

        return user.get();
    }

    public User login(LoginDto loginDto) {
        User user = findUserByUserEmail(loginDto.getUserEmail());

        if(!EncryptUtil.validateString(loginDto.getUserPassword(), user.getUserPassword()))
            throw new BusinessException(ErrorCode.USER_INCORRECT_PW);
        if(user.getUserStatus() == UserStatus.DELETED)
            throw new BusinessException(ErrorCode.USER_DELETED);
        if(user.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException(ErrorCode.USER_BLOCKED);

        return user;
    }

    public User findUserByUserEmail(String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        user.orElseThrow(() -> new EntityNotFoundException(userEmail, ErrorCode.USER_NOT_FOUND));

        return user.get();
    }

    @Transactional
    public void disabledUser(Integer id) {
        Optional<User> findUser = userRepository.findById(id);
        findUser.orElseThrow(() -> new EntityNotFoundException(id.toString(), ErrorCode.USER_NOT_FOUND));

        User user = findUser.get();
        user.disabledUser();
    }
}
