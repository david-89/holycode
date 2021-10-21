package com.holycode.placeManagement.exception;

public class GoogleApiCallException extends Exception {

    private static final long serialVersionUID = -7531357289482997361L;

    public GoogleApiCallException(String errorMessage) {
        super(errorMessage);
    }
}
