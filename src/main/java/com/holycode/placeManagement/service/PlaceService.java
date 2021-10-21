package com.holycode.placeManagement.service;

import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.dto.response.outbound.PlaceDto;
import com.holycode.placeManagement.exception.GoogleApiCallException;

public interface PlaceService {

    PlaceDto getPlace(String placeId) throws GoogleApiCallException;

    OpeningHoursDto getOpeningHours(String placeId);
}
