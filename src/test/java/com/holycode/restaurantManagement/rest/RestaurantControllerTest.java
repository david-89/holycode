package com.holycode.restaurantManagement.rest;

import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import com.holycode.restaurantManagement.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.holycode.restaurantManagement.util.UrlConstants.GET_RESTAURANT_BY_ID_URL;
import static com.holycode.restaurantManagement.util.UrlConstants.GET_RESTAURANT_OPENING_HOURS_URL;
import static com.holycode.restaurantManagement.util.UrlConstants.RESTAURANTS_ROOT_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private RestaurantService mockRestaurantService;

    private static final String RESTAURANT_ID = "restId1";

    private RestaurantDto getMockRestaurantDto() {
        return RestaurantDto.builder().name("Casa Bonita").addressLine("Some address 55").build();
    }

    private OpeningHoursDto getMockOpeningHours() {
        Map<String, List<String>> openingHours = new HashMap<>();
        openingHours.put("Monday - Friday", Arrays.asList("11:30 - 14:00", "18:30 - 22:00"));
        openingHours.put("Saturday - Sunday", Collections.singletonList("closed"));

        return new OpeningHoursDto(openingHours);
    }

    @Test
    public void getRestaurantByIdReturnsNameAndAddress() throws Exception {
        when(mockRestaurantService.getRestaurant(RESTAURANT_ID)).thenReturn(getMockRestaurantDto());

        MvcResult result = mockMvc.perform(get(RESTAURANTS_ROOT_URL + GET_RESTAURANT_BY_ID_URL, RESTAURANT_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Casa Bonita"))
            .andExpect(jsonPath("$.addressLine").value("Some address 55"))
            .andReturn();

        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

    @Test
    public void getOpeningHoursReturnsMockedData() throws Exception {
        when(mockRestaurantService.getOpeningHours(RESTAURANT_ID)).thenReturn(getMockOpeningHours());

        MvcResult result = mockMvc.perform(get(RESTAURANTS_ROOT_URL + GET_RESTAURANT_OPENING_HOURS_URL,
            RESTAURANT_ID).contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.openingHours.['Monday - Friday'].length()").value(2))
            .andExpect(jsonPath("$.openingHours.['Monday - Friday'][0]").value("11:30 - 14:00"))
            .andExpect(jsonPath("$.openingHours.['Monday - Friday'][1]").value("18:30 - 22:00"))
            .andExpect(jsonPath("$.openingHours.['Saturday - Sunday'][0]").value("closed"))
            .andExpect(jsonPath("$.openingHours.['Saturday - Sunday'].length()").value(1))
            .andReturn();

        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

}
