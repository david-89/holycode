package com.holycode.placeManagement.service;

import com.holycode.placeManagement.client.GoogleApiClient;
import com.holycode.placeManagement.dto.converter.PlaceDtoConverter;
import com.holycode.placeManagement.dto.response.inbound.InboundPlaceDto;
import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.dto.response.outbound.PlaceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final GoogleApiClient googleApiClient;

    private final PlaceDtoConverter placeDtoConverter;

    public PlaceServiceImpl(GoogleApiClient googleApiClient, PlaceDtoConverter placeDtoConverter) {
        this.googleApiClient = googleApiClient;
        this.placeDtoConverter = placeDtoConverter;
    }

    @Override
    public PlaceDto getPlace(String placeId) {
        InboundPlaceDto inboundPlaceDto;
        inboundPlaceDto = googleApiClient.getPlace(placeId);

        return placeDtoConverter.convertToOutboundDto(inboundPlaceDto);
    }

    @Override
    public OpeningHoursDto getOpeningHours(String placeId) {
        InboundPlaceDto inboundPlaceDto = googleApiClient.getPlace(placeId);
        return placeDtoConverter.convertToOpeningHoursDto(inboundPlaceDto);
    }
}
