package com.boot.api.globals.annotations.pagination;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PaginationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int offset = Integer.parseInt(webRequest.getParameter("offset"));
        int limit = Integer.parseInt(webRequest.getParameter("limit"));

        // sort 요청 샘플 /admin-user?offset=1&limit=3&sortBy=age,name&direction=desc,asc
        String sortBy = webRequest.getParameter("sortBy");
        String direction = webRequest.getParameter("direction");

        Sort sort = Sort.unsorted();
        if (sortBy != null && direction != null) {
            String[] sortByFields = sortBy.split(",");
            String[] directions = direction.split(",");

            if (sortByFields.length == directions.length) {
                List<Sort.Order> orders = new ArrayList<>();
                for (int i = 0; i < sortByFields.length; i++) {
                    Sort.Direction sortDirection = Sort.Direction.fromString(directions[i]);
                    orders.add(new Sort.Order(sortDirection, sortByFields[i]));
                }
                sort = Sort.by(orders);
            } else {
                throw new IllegalArgumentException("sortBy와 direction 갯수가 다름.");
            }
        }

        return PageRequest.of(offset, limit, sort);
    }
}
