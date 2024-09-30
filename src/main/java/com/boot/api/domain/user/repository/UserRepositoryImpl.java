package com.boot.api.domain.user.repository;

import com.boot.api.domain.user.dto.FindUserListDto;
import com.boot.api.domain.user.vo.FindUserListResultVo;
import com.boot.api.domain.user.vo.QFindUserListResultVo;
import com.boot.api.globals.session.UserSessionInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.boot.api.domain.user.entity.QUser.user;
import static io.jsonwebtoken.lang.Strings.hasText;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FindUserListResultVo> findUserList(FindUserListDto findUserListDto, UserSessionInfo userSessionInfo, Pageable pageable) {
        List<FindUserListResultVo> items = queryFactory.select(new QFindUserListResultVo(
            user.id,
            user.userEmail,
            user.phone,
            user.userStatus,
            user.role
        )).from(user)
        .where(
            userEmailEq(findUserListDto.getUserEmail()),
            phoneEq(findUserListDto.getPhone()))
        .orderBy(user.id.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

        Long count = queryFactory.select(user.count())
            .from(user)
            .where(
                userEmailEq(findUserListDto.getUserEmail()),
                phoneEq(findUserListDto.getPhone()))
            .fetchOne();

        return new PageImpl<>(items, pageable, count);
    }

    private BooleanExpression userEmailEq(String userEmail) {
        return hasText(userEmail) ? user.userEmail.eq(userEmail) : null;
    }

    private BooleanExpression phoneEq(String phone) {
        return hasText(phone) ? user.phone.eq(phone) : null;
    }
}
