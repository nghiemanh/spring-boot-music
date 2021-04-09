package com.acazia.music.controllers;

import com.acazia.music.base.BaseController;
import com.acazia.music.base.BaseResponseDto;
import com.acazia.music.base.CommonConstant;
import com.acazia.music.dto.produce.BaseListProduceDto;
import com.acazia.music.dto.produce.PlayListProduceDto;
import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.services.PlayListService;
import com.acazia.music.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/")
public class PlayerProfileController extends BaseController {

    @Autowired
    private SongService songService;

    @Autowired
    private PlayListService playListService;

    @GetMapping(value = "/{name}/songs")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<BaseResponseDto> getAllSongsByUserName(
            @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
            @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
            @PathVariable("name") String name,
            @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = getPageable(page, size, sort);
        Page<SongProduceDto> sponsorProduceDtoPage = songService.findSongByUserName(name, pageable);

        return success(BaseListProduceDto.build(sponsorProduceDtoPage), "get data successful.");
    }
}
