package com.cgi.ordermanagement.model.dto.security;


import com.cgi.ordermanagement.model.dto.AuditBaseDTO;
import com.cgi.ordermanagement.model.security.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AuditBaseDTO {

    private Long id;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 100)
    private String password;

    private String mobile;
    private String tenantId;

    @NotNull
    private UserType userType;

    List<GrantedAuthority> grantedAuths;

    public UserDTO() {

    }

    public UserDTO(Long id, @NotBlank @Size(max = 40) @Email String email, UserType userType, String tenantId, List<GrantedAuthority> grantedAuths) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.grantedAuths = grantedAuths;
        this.tenantId = tenantId;
    }


 /*   public UserDTO(Long id, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, UserType userType, List<GrantedAuthority> grantedAuths) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.grantedAuths = grantedAuths;
    }

    public UserDTO(Long id, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String password, List<GrantedAuthority> grantedAuths) {
        this.password = password;
        this.grantedAuths = grantedAuths;
    }
*/

}
