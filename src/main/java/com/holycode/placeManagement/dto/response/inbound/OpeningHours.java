package com.holycode.placeManagement.dto.response.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningHours implements Serializable {

    private static final long serialVersionUID = -6847609889695124918L;

    private Days days;
}
