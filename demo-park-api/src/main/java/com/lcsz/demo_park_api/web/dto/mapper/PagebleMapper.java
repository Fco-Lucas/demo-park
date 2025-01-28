package com.lcsz.demo_park_api.web.dto.mapper;

import com.lcsz.demo_park_api.web.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PagebleMapper {

    public static PageableDto toDto(Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }
}
