package com.holycode.restaurantManagement.dto.converter;

import com.holycode.restaurantManagement.dto.response.common.Days;
import com.holycode.restaurantManagement.dto.response.common.OpeningHoursData;
import com.holycode.restaurantManagement.dto.response.inbound.InboundRestaurantDto;
import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.holycode.restaurantManagement.util.RatingUtil.roundOffRating;

@Component
public class RestaurantDtoConverter {

    @Value("${restaurant.management.rating.roundOff.places}")
    private Integer roundOffPlaces;

    public RestaurantDto convertToOutboundDto(InboundRestaurantDto restaurantDto) {
        if (restaurantDto == null) {
            return new RestaurantDto();
        }

        return RestaurantDto.builder()
            .name(restaurantDto.getDisplayedWhat())
            .addressLine(restaurantDto.getDisplayedWhere())
            .averageRating(roundOffRating(restaurantDto.getPlaceFeedbackSummary().getAverageRating(), roundOffPlaces))
            .build();
    }

    public OpeningHoursDto convertToOpeningHoursDto(InboundRestaurantDto restaurantDto) {
        if (restaurantDto == null) {
            return new OpeningHoursDto();
        }
        Map<String, List<String>> hoursGroupedByDay = groupOpeningHoursByDay(restaurantDto.getOpeningHours().getDays());

        return OpeningHoursDto.builder()
            .openingHours(formatOpeningHours(hoursGroupedByDay))
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

    private Map<String, List<String>> groupOpeningHoursByDay(Days days) {
        if (days == null) {
            return Collections.emptyMap();
        }
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

        return daysHoursMap;
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
}
