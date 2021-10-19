package com.holycode.restaurantManagement.rest;

import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import com.holycode.restaurantManagement.service.RestaurantService;
import com.holycode.restaurantManagement.util.UrlConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.holycode.restaurantManagement.util.UrlConstants.GET_RESTAURANT_BY_ID_URL;
import static com.holycode.restaurantManagement.util.UrlConstants.GET_RESTAURANT_OPENING_HOURS_URL;

@RestController
@RequestMapping(value = UrlConstants.RESTAURANTS_ROOT_URL)
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(GET_RESTAURANT_BY_ID_URL)
    public RestaurantDto getRestaurant(@PathVariable("restaurantId") String restaurantId) {
        return restaurantService.getRestaurant(restaurantId);
    }

    @GetMapping(GET_RESTAURANT_OPENING_HOURS_URL)
    public OpeningHoursDto getOpeningHours(@PathVariable("restaurantId") String restaurantId) {
        return restaurantService.getOpeningHours(restaurantId);
    }

}
