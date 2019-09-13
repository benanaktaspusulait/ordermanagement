package com.cgi.ordermanagement.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO implements Request {

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20, message = "{size.signUpRequest.password}")
    private String password;

    private String firstName;
    private String lastName;
    private String tradingName;
    private String mobile;

}
