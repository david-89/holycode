package com.holycode.placeManagement.handlers;

import com.holycode.placeManagement.dto.response.outbound.ErrorDetails;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.holycode.placeManagement.util.ValidationConstants.CLIENT_ERROR_MSG;
import static com.holycode.placeManagement.util.ValidationConstants.RESOURCE_NOT_FOUND_ERROR_MSG;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpHeaders responseHeaders = new HttpHeaders();

    public GlobalExceptionHandler() {
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setType("/path-to-api-docs");
        errorDetails.setInstance(request.getRequestURI());

        if (ex.status() == HttpStatus.NOT_FOUND.value()) {
            errorDetails.setDetail(RESOURCE_NOT_FOUND_ERROR_MSG);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(responseHeaders).body(errorDetails);
        } else if (ex.status() >= 400 && ex.status() < 500) {
            errorDetails.setDetail(CLIENT_ERROR_MSG);
            errorDetails.setDetail(ex.getMessage());
            return ResponseEntity.badRequest().headers(responseHeaders).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(responseHeaders).build();
        }
    }
}
