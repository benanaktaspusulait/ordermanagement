package com.cgi.ordermanagement.model.dto.order;

import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderStatusChangeDTO {

    private Long id;
    private OrderStatus orderStatus;

}
