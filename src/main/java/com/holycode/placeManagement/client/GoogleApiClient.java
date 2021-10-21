package com.holycode.placeManagement.client;

import com.holycode.placeManagement.dto.response.inbound.InboundPlaceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.holycode.placeManagement.util.UrlConstants.GET_PLACE_BY_ID_URL;
import static com.holycode.placeManagement.util.UrlConstants.GOOGLE_API_ROOT_URL;

@FeignClient(value = "googleApiClient", url = "${google.api.base.uri}")
public interface GoogleApiClient {

    @GetMapping(GOOGLE_API_ROOT_URL + GET_PLACE_BY_ID_URL)
    InboundPlaceDto getPlace(@PathVariable("placeId") String placeId);

}
