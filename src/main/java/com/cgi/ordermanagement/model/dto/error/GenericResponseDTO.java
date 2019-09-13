package com.cgi.ordermanagement.model.dto.error;

import com.cgi.ordermanagement.config.CustomMessageSource;
import com.cgi.ordermanagement.config.StaticApplicationContext;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class GenericResponseDTO extends ErrorDTO {

    private Boolean success;
    private String successMessage;
    private HttpStatus status;

    public GenericResponseDTO(Integer errorCode) {

        if(errorCode.equals(0)){
            this.setSuccess(true);
            this.setSuccessMessage("OK");
        }else{
            CustomMessageSource messageSource = StaticApplicationContext.getContext().getBean(CustomMessageSource.class);
            this.setErrorDescription(messageSource.getErrorMessage(errorCode.toString()));
            this.setErrorCode(errorCode);
        }
    }

    public GenericResponseDTO( String error_description, Integer errorCode) {
        this.setErrorDescription(error_description);
        this.setErrorCode(errorCode);
    }


}
