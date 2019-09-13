package com.cgi.ordermanagement.model.dto.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleDTO {

    private Long id;
    private Long roleId;
    private Long userId;
    private UserDTO userDTO;
    private RoleDTO roleDTO;

}
