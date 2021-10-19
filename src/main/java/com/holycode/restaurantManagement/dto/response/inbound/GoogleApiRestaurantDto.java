package com.holycode.restaurantManagement.dto.response.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.holycode.restaurantManagement.dto.response.common.OpeningHours;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleApiRestaurantDto implements Serializable {

    private static final long serialVersionUID = -5289596808877237893L;

    private String displayedWhat;

    private String displayedWhere;

    private OpeningHours openingHours;

}
