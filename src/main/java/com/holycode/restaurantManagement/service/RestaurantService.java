package com.holycode.restaurantManagement.service;

import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;

public interface RestaurantService {

    RestaurantDto getRestaurant(String restaurantId);

    OpeningHoursDto getOpeningHours(String restaurantId);
}
