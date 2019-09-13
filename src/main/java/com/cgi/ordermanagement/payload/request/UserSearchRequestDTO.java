package com.cgi.ordermanagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSearchRequestDTO {

    private Long id;
    private String email;

}
