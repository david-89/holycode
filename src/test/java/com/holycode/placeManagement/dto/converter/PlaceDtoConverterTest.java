package com.holycode.placeManagement.dto.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holycode.placeManagement.dto.response.inbound.OpeningHoursData;
import com.holycode.placeManagement.dto.response.inbound.InboundPlaceDto;
import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.mockUtils.JsonToJavaConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = PlaceDtoConverter.class)
public class PlaceDtoConverterTest {

    @Autowired
    private PlaceDtoConverter converter;

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
        JsonToJavaConverter<InboundPlaceDto> jsonConverter = new JsonToJavaConverter<>(objectMapper, fullOpeningHoursFilePath);
        InboundPlaceDto mockInboundPlaceDto = jsonConverter.loadJson(InboundPlaceDto.class);
        OpeningHoursDto result = converter.convertToOpeningHoursDto(mockInboundPlaceDto);

        assertEquals(4, result.getOpeningHours().size());
        assertEquals(OpeningHoursData.OpeningType.CLOSED.name().toLowerCase(), result.getOpeningHours().get("Monday").get(0));
        assertEquals(Arrays.asList("11:30 - 15:00", "18:30 - 00:00"), result.getOpeningHours().get("Tuesday - Friday"));
        assertEquals(Collections.singletonList("18:00 - 00:00"), result.getOpeningHours().get("Saturday"));
        assertEquals(Collections.singletonList("11:30 - 15:00"), result.getOpeningHours().get("Sunday"));
    }

    @Test
    public void convertToOpeningHoursDtoReturnsEmptyMapWhenEmptyOpeningHours() throws Exception {
        JsonToJavaConverter<InboundPlaceDto> jsonConverter = new JsonToJavaConverter<>(objectMapper, emptyOpeningHoursFilePath);
        InboundPlaceDto mockInboundPlaceDto = jsonConverter.loadJson(InboundPlaceDto.class);
        OpeningHoursDto result = converter.convertToOpeningHoursDto(mockInboundPlaceDto);

        assertEquals(0, result.getOpeningHours().size());
    }
}
