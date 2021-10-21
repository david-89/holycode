package com.holycode.placeManagement.dto.response.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningHoursData implements Serializable {

    private static final long serialVersionUID = 8080661759183849615L;

    private String start = "";

    private String end = "";

    private OpeningType type = OpeningType.OPEN;

    public enum OpeningType {
        OPEN, CLOSED
    }
}
