package com.cgi.ordermanagement.model.dto.system;

import com.cgi.ordermanagement.model.system.SystemError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemErrorDTO implements Serializable {

    private Long id;
    private Integer errorCode;
    private String username;
    private String errorDescription;

    public SystemErrorDTO(Integer errorCode, String username, String errorDescription){
        this.errorCode = errorCode;
        this.username = username;
        this.errorDescription = errorDescription;
    }

    public SystemErrorDTO(Integer errorCode, String username, Throwable ex){
        this.errorCode = errorCode;
        this.username = username;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        this.errorDescription = sw.toString();
    }

    public static SystemError toEntity(SystemErrorDTO dto) {

        SystemError entity = new SystemError();
        entity.setId(dto.getId());
        entity.setErrorCode(dto.getErrorCode());
        entity.setUsername(dto.getUsername());
        entity.setErrorDescription(dto.getErrorDescription());
        return entity;

    }


}
