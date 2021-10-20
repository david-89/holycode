package com.holycode.restaurantManagement.dto.response.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorDetails implements Serializable {

    private static final long serialVersionUID = 8888713080032046866L;

    private String detail;

    private String type;

    private String instance;
}
