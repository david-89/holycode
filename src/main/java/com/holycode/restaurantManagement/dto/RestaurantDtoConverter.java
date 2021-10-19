package com.holycode.restaurantManagement.dto;

import com.holycode.restaurantManagement.dto.response.common.Days;
import com.holycode.restaurantManagement.dto.response.inbound.GoogleApiRestaurantDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantDtoConverter {

    public RestaurantDto convertToOutboundDto(GoogleApiRestaurantDto googleApiRestaurantDto) {
        if (googleApiRestaurantDto == null) {
            return new RestaurantDto();
        }
        Map<String, List<String>> map = groupOpeningHoursByDay(googleApiRestaurantDto.getOpeningHours().getDays());
        Map<String, List<String>> res = new LinkedHashMap<>();
        map.values().forEach(value -> {
            value.forEach(shift -> {
                String key = getKeysFromValue(map, value);
                res.put(key, value);
            });

        });



        return RestaurantDto.builder()
            .name(googleApiRestaurantDto.getDisplayedWhat())
            .addressLine(googleApiRestaurantDto.getDisplayedWhere())
            .openingHours(res)
            .build();
    }

    private Map<String, List<String>> groupOpeningHoursByDay(Days days) {
        Map<String, List<String>> daysHoursMap = new LinkedHashMap<>();

        daysHoursMap.put("monday", new ArrayList<>());
        daysHoursMap.put("tuesday", new ArrayList<>());
        daysHoursMap.put("wednesday", new ArrayList<>());
        daysHoursMap.put("thursday", new ArrayList<>());
        daysHoursMap.put("friday", new ArrayList<>());
        daysHoursMap.put("saturday", new ArrayList<>());
        daysHoursMap.put("sunday", new ArrayList<>());

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

    private String getKeysFromValue(Map<String, List<String>> map, List<String> valueList) {
        LinkedHashSet<String> keys = new LinkedHashSet<>();
        for (String key: map.keySet())
        {
            if (valueList.equals(map.get(key))) {
                keys.add(key);
            }
        }



        return String.join(" - ", keys);
    }
}
