package com.telusko.joblisting.exception.code;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiException {

    private HttpStatus statusCode;
    private String statusMessage;
    private String debugMessage;
}
