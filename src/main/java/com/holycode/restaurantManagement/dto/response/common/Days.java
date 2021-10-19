package com.holycode.restaurantManagement.dto.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Days implements Serializable {

    private static final long serialVersionUID = -5616809842164980379L;

    private List<OpeningHoursData> monday = new ArrayList<>();

    private List<OpeningHoursData> tuesday = new ArrayList<>();

    private List<OpeningHoursData> wednesday = new ArrayList<>();

    private List<OpeningHoursData> thursday = new ArrayList<>();

    private List<OpeningHoursData> friday = new ArrayList<>();

    private List<OpeningHoursData> saturday = new ArrayList<>();

    private List<OpeningHoursData> sunday = new ArrayList<>();
}
