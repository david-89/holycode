package com.holycode.placeManagement.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RatingUtil {

    public static BigDecimal roundOffRating(Double averageRating, int places) {
        BigDecimal bd = BigDecimal.valueOf(averageRating);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd;
    }
}
