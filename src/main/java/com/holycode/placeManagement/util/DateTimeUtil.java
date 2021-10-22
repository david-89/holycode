package com.holycode.placeManagement.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    public static String getTodayDay() {
        return LocalDateTime.now().getDayOfWeek().toString().toLowerCase();
    }

    public static LocalTime getNow() {
        return LocalTime.now();
    }

    public static boolean isCurrentTime(LocalTime startTime, LocalTime endTime) {
        LocalTime now = getNow();
        return (now.isAfter(startTime) || now.equals(startTime)) && now.isBefore(endTime);
    }
}
