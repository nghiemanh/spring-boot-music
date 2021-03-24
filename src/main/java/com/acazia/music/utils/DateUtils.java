package com.acazia.music.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class DateUtils {

    private DateUtils() {
    }

    public static Date convertToUtilDate(LocalDateTime dateToConvert) {
        if (Objects.isNull(dateToConvert)) {
            return null;
        }

        return Date.from(dateToConvert
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
