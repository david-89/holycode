package com.holycode.restaurantManagement.service;

import com.holycode.restaurantManagement.client.GoogleApiClient;
import com.holycode.restaurantManagement.dto.converter.RestaurantDtoConverter;
import com.holycode.restaurantManagement.dto.response.inbound.InboundRestaurantDto;
import com.holycode.restaurantManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.restaurantManagement.dto.response.outbound.RestaurantDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final GoogleApiClient googleApiClient;

    private final RestaurantDtoConverter restaurantDtoConverter;

    public RestaurantServiceImpl(GoogleApiClient googleApiClient, RestaurantDtoConverter restaurantDtoConverter) {
        this.googleApiClient = googleApiClient;
        this.restaurantDtoConverter = restaurantDtoConverter;
    }

    @Override
    public RestaurantDto getRestaurant(String restaurantId) {
        InboundRestaurantDto inboundRestaurantDto;
        inboundRestaurantDto = googleApiClient.getRestaurant(restaurantId);

        return restaurantDtoConverter.convertToOutboundDto(inboundRestaurantDto);
    }

    @Override
    public OpeningHoursDto getOpeningHours(String restaurantId) {
        InboundRestaurantDto inboundRestaurantDto = googleApiClient.getRestaurant(restaurantId);
        return restaurantDtoConverter.convertToOpeningHoursDto(inboundRestaurantDto);
    }
}
