package com.acazia.music.utils;

import com.acazia.music.base.interfaces.BaseEnum;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class EnumUtils {
    private EnumUtils() {

    }

    public static <T extends BaseEnum> Optional<T> getEnumByCode(Class<T> clazz, Integer code) {
        if (Objects.isNull(code)) {
            return Optional.empty();
        }

        return Arrays.stream(clazz.getEnumConstants()).filter(t -> code.equals(t.getCode())).findFirst();
    }
}
