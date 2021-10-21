package com.holycode.placeManagement.service;

import com.holycode.placeManagement.dto.response.outbound.OpeningHoursDto;
import com.holycode.placeManagement.dto.response.outbound.PlaceDto;

public interface PlaceService {

    PlaceDto getPlace(String placeId);

    OpeningHoursDto getOpeningHours(String placeId);
}
