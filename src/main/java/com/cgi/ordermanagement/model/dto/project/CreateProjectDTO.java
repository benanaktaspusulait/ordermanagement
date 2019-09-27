package com.cgi.ordermanagement.model.dto.project;

import com.cgi.ordermanagement.model.enums.order.OrderType;
import com.cgi.ordermanagement.model.project.Dealer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProjectDTO {

    private String name;
    private Dealer dealer;
    private Long dealerId;

}
