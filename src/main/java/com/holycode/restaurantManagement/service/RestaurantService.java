package com.holycode.restaurantManagement.service;

import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import com.holycode.restaurantManagement.exception.GoogleApiCallException;

public interface RestaurantService {

    RestaurantDto getRestaurant(String restaurantId) throws GoogleApiCallException;

    OpeningHoursDto getOpeningHours(String restaurantId);
}
