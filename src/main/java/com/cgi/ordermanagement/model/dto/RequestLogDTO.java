package com.cgi.ordermanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestLogDTO {

    private String methodType;
    private String methodName;
    private String parameters;
    private Long authenticatedUserId;
    private String requestId;
}
