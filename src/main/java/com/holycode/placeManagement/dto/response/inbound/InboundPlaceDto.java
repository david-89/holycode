package com.holycode.placeManagement.dto.response.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InboundPlaceDto implements Serializable {

    private static final long serialVersionUID = -5289596808877237893L;

    private String displayedWhat;

    private String displayedWhere;

    private OpeningHours openingHours;

    private PlaceFeedbackSummary placeFeedbackSummary;

}
