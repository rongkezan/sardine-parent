package com.sardine.common.utils;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author keith
 */
public class LocalDateTimeUtils {

    public static final ZoneId ZONE_ID_CCT = TimeZone.getTimeZone("GMT+8:00").toZoneId();

    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID_CCT);
    }

    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, null);
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = StringUtils.isEmpty(pattern) ? DEFAULT_FORMATTER : DateTimeFormatter.ofPattern(pattern);
        return formatter.format(localDateTime);
    }

    public static LocalDateTime parseTimestamp(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_CCT).toLocalDateTime();
    }
}
