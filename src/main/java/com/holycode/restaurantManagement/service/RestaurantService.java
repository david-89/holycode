package com.holycode.restaurantManagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;

public interface RestaurantService {

    RestaurantDto getRestaurant(String restaurantId) throws JsonProcessingException;
}
