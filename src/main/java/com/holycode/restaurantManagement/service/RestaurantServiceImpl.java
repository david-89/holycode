package com.holycode.restaurantManagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.holycode.restaurantManagement.client.GoogleApiClient;
import com.holycode.restaurantManagement.dto.RestaurantDtoConverter;
import com.holycode.restaurantManagement.dto.response.inbound.GoogleApiRestaurantDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final GoogleApiClient googleApiClient;

    private final RestaurantDtoConverter restaurantDtoConverter;

    public RestaurantServiceImpl(GoogleApiClient googleApiClient, RestaurantDtoConverter restaurantDtoConverter) {
        this.googleApiClient = googleApiClient;
        this.restaurantDtoConverter = restaurantDtoConverter;
    }

    @Override
    public RestaurantDto getRestaurant(String restaurantId) throws JsonProcessingException {
        GoogleApiRestaurantDto googleApiRestaurant = googleApiClient.getRestaurant(restaurantId);
        return restaurantDtoConverter.convertToOutboundDto(googleApiRestaurant);
    }
}