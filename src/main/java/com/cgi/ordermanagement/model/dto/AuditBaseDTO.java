package com.cgi.ordermanagement.model.dto;

import com.cgi.ordermanagement.model.enums.DataStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class AuditBaseDTO {

    public Date createdAt;
    private Date updatedAt;
    private Long createdBy = 1L;
    private Long lastModifiedBy;
    private DataStatus dataStatus = DataStatus.ACTIVE;

    @JsonIgnore
    private Long version;
}
