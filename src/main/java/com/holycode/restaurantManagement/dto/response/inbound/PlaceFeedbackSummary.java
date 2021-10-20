package com.holycode.restaurantManagement.dto.response.inbound;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PlaceFeedbackSummary implements Serializable {

    private static final long serialVersionUID = -7706010658766355514L;

    private Double averageRating;
}
