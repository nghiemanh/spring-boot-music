package com.acazia.music.enums;

import com.acazia.music.base.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RoleType implements IBaseEnum {
	USER("USER"),
    ADMIN("ADMIN");

    private final String name;
}
