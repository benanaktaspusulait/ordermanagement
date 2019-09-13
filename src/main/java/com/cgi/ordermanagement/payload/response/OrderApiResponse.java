package com.cgi.ordermanagement.payload.response;

import lombok.Data;

@Data
public class OrderApiResponse {
    private Boolean success;
    private String message;
    private Long id;

    public OrderApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
