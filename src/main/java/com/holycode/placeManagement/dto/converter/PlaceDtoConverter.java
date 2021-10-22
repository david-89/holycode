package com.holycode.placeManagement.dto.converter;

import com.holycode.placeManagement.dto.response.inbound.Days;
import com.holycode.placeManagement.dto.response.inbound.InboundPlaceDto;
import com.holycode.placeManagement.dto.response.inbound.OpeningHoursData;
import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.dto.response.outbound.PlaceDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.holycode.placeManagement.util.DateTimeUtil.getTodayDay;
import static com.holycode.placeManagement.util.DateTimeUtil.isCurrentTime;
import static com.holycode.placeManagement.util.RatingUtil.roundOffRating;

@Component
public class PlaceDtoConverter {

    @Value("${place.management.rating.roundOff.places}")
    private Integer roundOffPlaces;

    public PlaceDto convertToOutboundDto(InboundPlaceDto placeDto) {
        if (placeDto == null) {
            return new PlaceDto();
        }

        return PlaceDto.builder()
            .name(placeDto.getDisplayedWhat())
            .addressLine(placeDto.getDisplayedWhere())
            .averageRating(roundOffRating(placeDto.getPlaceFeedbackSummary().getAverageRating(), roundOffPlaces))
            .build();
    }

    public OpeningHoursDto convertToOpeningHoursDto(InboundPlaceDto placeDto) {
        if (placeDto == null) {
            return new OpeningHoursDto();
        }
        GroupedShiftsData groupedShiftsData = groupOpeningHoursByDay(placeDto.getOpeningHours().getDays());

        return OpeningHoursDto.builder()
            .openingHours(formatOpeningHours(groupedShiftsData.getGroupedHoursByDay()))
            .openNow(groupedShiftsData.isOpenNow)
            .build();
    }

    private Map<String, List<String>> formatOpeningHours(Map<String, List<String>> hoursGroupedByDay) {
        Map<String, List<String>> openingHoursFormatted = new LinkedHashMap<>();
        hoursGroupedByDay.values().forEach(hoursList -> {
            hoursList.forEach(shift -> {
                List<String> hoursListCopy = new ArrayList<>(hoursList);
                String daysWithCommonOpeningHours = getDaysSequenceWithCommonOpeningHours(hoursGroupedByDay, hoursList);
                String closed = OpeningHoursData.OpeningType.CLOSED.name().toLowerCase();
                if (hoursList.size() > 1 && hoursList.contains(closed)) {
                    hoursListCopy.remove(closed);
                }
                openingHoursFormatted.put(daysWithCommonOpeningHours, hoursListCopy);
            });
        });

        return openingHoursFormatted;
    }

    private GroupedShiftsData groupOpeningHoursByDay(Days days) {

        if (days == null) {
            return new GroupedShiftsData();
        }
        GroupedShiftsData groupedShiftsData = new GroupedShiftsData();
        Map<String, List<String>> daysHoursMap = new LinkedHashMap<>();
        initMapWithDays(daysHoursMap);

        days.getMonday().forEach(hoursData -> {
            daysHoursMap.get("monday").add(hoursData.getStart() + " - " + hoursData.getEnd());
        });

        days.getTuesday().forEach(hoursData -> {
            daysHoursMap.get("tuesday").add(hoursData.getStart() + " - " + hoursData.getEnd());
        });

        days.getWednesday().forEach(hoursData -> {
            daysHoursMap.get("wednesday").add(hoursData.getStart() + " - " + hoursData.getEnd());

        });

        days.getThursday().forEach(hoursData -> {
            daysHoursMap.get("thursday").add(hoursData.getStart() + " - " + hoursData.getEnd());
        });

        days.getFriday().forEach(hoursData -> {
            daysHoursMap.get("friday").add(hoursData.getStart() + " - " + hoursData.getEnd());
        });

        days.getSaturday().forEach(hoursData -> {
            daysHoursMap.get("saturday").add(hoursData.getStart() + " - " + hoursData.getEnd());

        });

        days.getSunday().forEach(hoursData -> {
            daysHoursMap.get("sunday").add(hoursData.getStart() + " - " + hoursData.getEnd());
        });

        groupedShiftsData.setGroupedHoursByDay(daysHoursMap);
        groupedShiftsData.setOpenNow(isOpenNow(daysHoursMap));

        return groupedShiftsData;
    }

    private void initMapWithDays(Map<String, List<String>> daysMap) {
        for (int i = 1; i <= 7; i++) {
            List<String> hours = new ArrayList<>();
            hours.add(OpeningHoursData.OpeningType.CLOSED.name().toLowerCase());
            daysMap.put(DayOfWeek.of(i).toString().toLowerCase(), hours);
        }
    }

    private String getDaysSequenceWithCommonOpeningHours(Map<String, List<String>> hoursGroupedByDay, List<String> hoursList) {
        LinkedHashSet<String> days = new LinkedHashSet<>();
        for (String day : hoursGroupedByDay.keySet()) {
            if (hoursList.equals(hoursGroupedByDay.get(day))) {
                days.add(day);
            }
        }

        List<String> startAndEndDay = new ArrayList<>(days);

        String startDay = startAndEndDay.get(0);
        startDay = startDay.substring(0, 1).toUpperCase() + startDay.substring(1);
        if (startAndEndDay.size() == 1) {
            return startDay;
        }

        String endDay = startAndEndDay.get(startAndEndDay.size() - 1);
        endDay = endDay.substring(0, 1).toUpperCase() + endDay.substring(1);

        return startDay + " - " + endDay;
    }

    private boolean isOpenNow(Map<String, List<String>> openingHoursGroupedByDay) {
        List<String> shifts = openingHoursGroupedByDay.get(getTodayDay());

        return shifts.stream().anyMatch(shift -> {
            String[] startTimeAndEndTime = shift.split(" - ");
            if (startTimeAndEndTime.length == 2) {
                String[] startHourAndMinute = startTimeAndEndTime[0].split(":");
                String startHour = startHourAndMinute[0];
                String startMinute = startHourAndMinute[1];

                LocalTime startTime = LocalTime.of(Integer.parseInt(startHour), Integer.parseInt(startMinute));

                String[] endHourAndMinute = startTimeAndEndTime[1].split(":");
                String endHour = endHourAndMinute[0];
                String endMinute = endHourAndMinute[1];

                LocalTime endTime = LocalTime.of(Integer.parseInt(endHour), Integer.parseInt(endMinute));

                return isCurrentTime(startTime, endTime);
            }
            return false;
        });
    }

    @Data
    public static class GroupedShiftsData {

        private Map<String, List<String>> groupedHoursByDay;

        private boolean isOpenNow;
    }
}
