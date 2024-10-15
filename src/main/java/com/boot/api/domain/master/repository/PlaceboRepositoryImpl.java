package com.boot.api.domain.master.repository;

import com.boot.api.domain.master.dto.FindPlaceboListDto;
import com.boot.api.domain.master.vo.FindPlaceboListVo;
import com.boot.api.domain.master.vo.QFindPlaceboListVo;
import com.boot.api.domain.user.vo.QFindUserListResultVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.boot.api.domain.master.entity.QPlacebo.placebo;
import static com.boot.api.globals.common.enums.YesNo.Y;
import static io.jsonwebtoken.lang.Strings.hasText;

@RequiredArgsConstructor
public class PlaceboRepositoryImpl implements PlaceboRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<FindPlaceboListVo> findPlaceboList(FindPlaceboListDto findPlaceboListDto, Pageable pageable) {
        List<FindPlaceboListVo> items = queryFactory.select(new QFindPlaceboListVo(
                placebo.id,
                placebo.dateId,
                placebo.name,
                placebo.phone,
                placebo.startDate,
                placebo.endDate,
                placebo.placeboStatus,
                placebo.note))
        .from(placebo)
        .where(nameEq(findPlaceboListDto),
            phoneEq(findPlaceboListDto),
            statusEq(findPlaceboListDto),
            placebo.isDeletedYn.ne(Y))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

        Long count = queryFactory.select(placebo.count())
            .from(placebo)
            .where(nameEq(findPlaceboListDto),
                phoneEq(findPlaceboListDto),
                statusEq(findPlaceboListDto),
                placebo.isDeletedYn.ne(Y))
            .fetchOne();

        return new PageImpl<>(items, pageable, count);
    }

    private BooleanExpression nameEq(FindPlaceboListDto findPlaceboListDto) {
        return hasText(findPlaceboListDto.getName()) ? placebo.name.eq(findPlaceboListDto.getName()) : null;
    }

    private BooleanExpression phoneEq(FindPlaceboListDto findPlaceboListDto) {
        return hasText(findPlaceboListDto.getPhone()) ? placebo.phone.eq(findPlaceboListDto.getPhone()) : null;
    }

    private BooleanExpression statusEq(FindPlaceboListDto findPlaceboListDto) {
        return hasText(findPlaceboListDto.getPlaceboStatus().getValue()) ? placebo.placeboStatus.eq(findPlaceboListDto.getPlaceboStatus()) : null;
    }
}
