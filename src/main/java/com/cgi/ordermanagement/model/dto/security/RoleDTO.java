package com.cgi.ordermanagement.model.dto.security;

import com.cgi.ordermanagement.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private Long id;
    private RoleName name;

}
