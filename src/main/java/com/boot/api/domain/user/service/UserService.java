package com.boot.api.domain.user.service;

import com.boot.api.domain.user.entity.User;
import com.boot.api.domain.user.repository.UserRepository;
import com.boot.api.globals.common.enums.ErrorCode;
import com.boot.api.globals.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByUserEmail(String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        user.orElseThrow(() -> new EntityNotFoundException(userEmail, ErrorCode.USER_NOT_FOUND));

        return user.get();
    }
}
