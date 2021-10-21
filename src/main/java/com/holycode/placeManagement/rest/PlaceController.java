package com.holycode.placeManagement.rest;

import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.dto.response.outbound.PlaceDto;
import com.holycode.placeManagement.exception.GoogleApiCallException;
import com.holycode.placeManagement.service.PlaceService;
import com.holycode.placeManagement.util.UrlConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.holycode.placeManagement.util.UrlConstants.GET_PLACE_BY_ID_URL;
import static com.holycode.placeManagement.util.UrlConstants.GET_PLACE_OPENING_HOURS_URL;

@RestController
@RequestMapping(value = UrlConstants.PLACES_ROOT_URL)
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(GET_PLACE_BY_ID_URL)
    public PlaceDto getPlace(@PathVariable("placeId") String placeId) throws GoogleApiCallException {
        return placeService.getPlace(placeId);
    }

    @GetMapping(GET_PLACE_OPENING_HOURS_URL)
    public OpeningHoursDto getOpeningHours(@PathVariable("placeId") String placeId) {
        return placeService.getOpeningHours(placeId);
    }

}
