package com.holycode.restaurantManagement.dto.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holycode.restaurantManagement.dto.response.common.OpeningHoursData;
import com.holycode.restaurantManagement.dto.response.inbound.GoogleApiRestaurantDto;
import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.mockUtils.JsonToJavaConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RestaurantDtoConverter.class)
public class RestaurantDtoConverterTest {

    @Autowired
    private RestaurantDtoConverter converter;

    private static String fullOpeningHoursFilePath;

    private static String emptyOpeningHoursFilePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setupData() {
        File[] testDataFiles = Paths.get("src", "test", "resources").toFile().getAbsoluteFile().getAbsoluteFile().listFiles();
        fullOpeningHoursFilePath = Arrays.stream(testDataFiles)
            .filter(file -> "full-opening-hours.json".equals(file.getName()))
            .findFirst().get()
            .getAbsolutePath();

        emptyOpeningHoursFilePath = Arrays.stream(testDataFiles)
            .filter(file -> "empty-opening-hours.json".equals(file.getName()))
            .findFirst().get()
            .getAbsolutePath();
    }

    @Test
    public void convertToOpeningHoursDtoFormatsOpeningHoursProperly() throws Exception {
        JsonToJavaConverter<GoogleApiRestaurantDto> jsonConverter = new JsonToJavaConverter<>(objectMapper, fullOpeningHoursFilePath);
        GoogleApiRestaurantDto mockGoogleApiRestaurantDto = jsonConverter.loadJson(GoogleApiRestaurantDto.class);
        OpeningHoursDto result = converter.convertToOpeningHoursDto(mockGoogleApiRestaurantDto);

        assertEquals(4, result.getOpeningHours().size());
        assertEquals(OpeningHoursData.OpeningType.CLOSED.name().toLowerCase(), result.getOpeningHours().get("Monday").get(0));
        assertEquals(Arrays.asList("11:30 - 15:00", "18:30 - 00:00"), result.getOpeningHours().get("Tuesday - Friday"));
        assertEquals(Collections.singletonList("18:00 - 00:00"), result.getOpeningHours().get("Saturday"));
        assertEquals(Collections.singletonList("11:30 - 15:00"), result.getOpeningHours().get("Sunday"));
    }

    @Test
    public void convertToOpeningHoursDtoReturnsEmptyMapWhenEmptyOpeningHours() throws Exception {
        JsonToJavaConverter<GoogleApiRestaurantDto> jsonConverter = new JsonToJavaConverter<>(objectMapper, emptyOpeningHoursFilePath);
        GoogleApiRestaurantDto mockGoogleApiRestaurantDto = jsonConverter.loadJson(GoogleApiRestaurantDto.class);
        OpeningHoursDto result = converter.convertToOpeningHoursDto(mockGoogleApiRestaurantDto);

        assertEquals(0, result.getOpeningHours().size());
    }
}
