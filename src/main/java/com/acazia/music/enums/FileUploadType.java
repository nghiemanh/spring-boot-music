package com.acazia.music.enums;

import com.acazia.music.base.interfaces.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileUploadType implements BaseEnum {
    OTHER(0, "other"),
    SONG(1, "song"),
    AVATAR(2, "avatar");
    private final Integer code;
    private final String description;

}
