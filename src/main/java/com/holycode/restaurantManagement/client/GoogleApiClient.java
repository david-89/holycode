package com.holycode.restaurantManagement.client;

import com.holycode.restaurantManagement.dto.response.inbound.GoogleApiRestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.holycode.restaurantManagement.util.UrlConstants.GET_RESTAURANT_BY_ID_URL;
import static com.holycode.restaurantManagement.util.UrlConstants.GOOGLE_API_ROOT_URL;

@FeignClient(value = "googleApiClient", url = "${google.api.base.uri}")
public interface GoogleApiClient {

    @GetMapping(GOOGLE_API_ROOT_URL + GET_RESTAURANT_BY_ID_URL)
    GoogleApiRestaurantDto getRestaurant(@PathVariable("restaurantId") String restaurantId);

}
