package com.cgi.ordermanagement.model.dto.order;

import com.cgi.ordermanagement.model.enums.order.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderDTO {

    private @NotNull Double quantity;
    private @NotNull Double price;
    private @NotNull OrderType orderType;
    @JsonIgnore
    private Long userId;
}
