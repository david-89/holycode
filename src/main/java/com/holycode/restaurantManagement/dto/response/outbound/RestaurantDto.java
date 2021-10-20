package com.holycode.restaurantManagement.dto.response.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto implements Serializable {

    private static final long serialVersionUID = -300553145013893898L;

    private String name;

    private String addressLine;

    private BigDecimal averageRating;
}
