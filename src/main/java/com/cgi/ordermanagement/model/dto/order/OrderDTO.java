package com.cgi.ordermanagement.model.dto.order;

import com.cgi.ordermanagement.model.dto.AuditBaseDTO;
import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.model.enums.order.OrderType;
import com.cgi.ordermanagement.model.order.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO extends AuditBaseDTO {

    private Long id;
    private @NotNull Double quantity;
    private @NotNull Double price;
    private @NotNull OrderType orderType;
    private @NotNull OrderStatus orderStatus;
    private @NotNull Long userId;

    public static Order toEntity(OrderDTO dto, Order entity) {

        entity = entity == null ? new Order() : entity;
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setOrderType(dto.getOrderType());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setUserId(dto.getUserId());
        return entity;
    }


}
