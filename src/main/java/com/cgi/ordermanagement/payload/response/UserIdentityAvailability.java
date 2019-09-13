package com.cgi.ordermanagement.payload.response;

import lombok.Data;

@Data
public class UserIdentityAvailability {

    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

}
