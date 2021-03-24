package com.acazia.music.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BaseController {

    protected ResponseEntity<BaseResponseDto> success(Object data, String message) {
        return ResponseEntity.ok(BaseResponseDto.success(data, message));
    }

    protected Sort.Direction getSortDirection(String direction) {
        if (Sort.Direction.ASC.name().equalsIgnoreCase(direction)) {
            return Sort.Direction.ASC;
        } else if (Sort.Direction.DESC.name().equalsIgnoreCase(direction)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.DESC;
    }

    protected List<Sort.Order> getListOrder(String[] sort) {
        if (Objects.isNull(sort)) {
            sort = new String[]{"id:desc"};
        }
        // when haven't sort by fields, then will sort by Id, desc
        List<Sort.Order> orders = new LinkedList<>();
        Arrays.stream(sort).forEach(sortFields -> {
            //when sort has (":") and have desc or asc
            if (sortFields.contains(":")) {
                if (sortFields.endsWith(":")) {
                    //when sort has (":") but haven't asc or desc
                    orders.add(new Sort.Order(Sort.Direction.DESC, sortFields.substring(0, sortFields.length() - 1)));
                } else {
                    String[] _sort = sortFields.split(":");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }

            } else {
                //when sort haven't (":") and haven't desc or asc
                orders.add(new Sort.Order(Sort.Direction.DESC, sortFields));
            }
        });

        return orders;
    }

    protected Pageable getPageable(int page, int size, String[] sort) {
        return PageRequest.of(page, size, Sort.by(getListOrder(sort)));
    }
}
