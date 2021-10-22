package com.holycode.placeManagement.dto.response.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursDto implements Serializable {

    private static final long serialVersionUID = -3636213004165581835L;

    private Map<String, List<String>> openingHours;

    private boolean openNow;
}
