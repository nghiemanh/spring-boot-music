package com.acazia.music.dto.produce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class BaseListProduceDto<T> {
    private final List<T> content;
    private final Integer page;
    private final Integer size;
    @JsonProperty("total_pages")
    private final Integer totalPages;
    @JsonProperty("total_elements")
    private final Long totalElements;


    public static <T> BaseListProduceDto<T> build(Page<T> page) {
        return BaseListProduceDto
                .<T>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .build();
    }

}
