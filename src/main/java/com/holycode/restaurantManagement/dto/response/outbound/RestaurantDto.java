package com.holycode.restaurantManagement.dto.response.outbound;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto implements Serializable {

    private static final long serialVersionUID = -300553145013893898L;

    private String name;

    private String addressLine;

    private Map<String, List<String>> openingHours;
}
