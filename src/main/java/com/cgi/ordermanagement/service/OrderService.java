package com.cgi.ordermanagement.service;

import com.cgi.ordermanagement.model.dto.order.CreateOrderDTO;
import com.cgi.ordermanagement.model.dto.order.OrderDTO;
import com.cgi.ordermanagement.model.dto.order.OrderStatusChangeDTO;
import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.model.enums.order.OrderType;
import com.cgi.ordermanagement.model.order.Order;
import com.cgi.ordermanagement.repository.order.OrderRepository;
import com.cgi.ordermanagement.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService extends BaseService<OrderDTO, OrderRepository> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    /**
     *  create a new order
     * @param dto
     * @return
     */
    @Transactional
    public OrderDTO save(CreateOrderDTO dto) {

        log.debug("Request to save User : {}", dto);
        dto.setUserId(SecurityUtils.getUserId());
        Order entity = modelMapper.map(dto, Order.class);
        Order result = orderRepository.save(entity);
        return modelMapper.map(result, OrderDTO.class);
    }

    /**
     * update an existing order
     * @param dto
     * @return
     */
    @Transactional
    public OrderDTO update(OrderDTO dto) {

        log.debug("Request to update User : {}", dto);
        Order entity;
        dto.setUserId(SecurityUtils.getUserId());
        if (dto.getId() != null) {
            entity = orderRepository.findById(dto.getId()).get();
            entity = OrderDTO.toEntity(dto, entity);
        } else {
            entity = modelMapper.map(dto, Order.class);
        }
        Order result = orderRepository.save(entity);
        return modelMapper.map(result, OrderDTO.class);
    }

    /**
     * delete an order
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    /**
     * update an existing Order Status
     * @param dto
     */
    @Transactional
    public void updateOrderStatus(OrderStatusChangeDTO dto) {
        log.debug("Request to change status Order : {}", dto);
        orderRepository.changeOrderStatus(dto.getOrderStatus(),dto.getId());
    }

    /**
     * to get all orders pageable
     * @param pageable
     * @return
     */
    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(order -> modelMapper.map(order, OrderDTO.class));
    }

    /**
     * to get live order
     * @param orderStatus
     * @return
     */
    public Map<OrderType,Map<Double,Double>> findByOrderStatus( OrderStatus orderStatus) {

        Map<OrderType,Map<Double,Double>> priceQuantityMap = orderRepository.findByOrderStatus(orderStatus).stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList())
                .stream().collect(Collectors.groupingBy(OrderDTO::getOrderType,
                        Collectors.groupingBy(OrderDTO::getPrice, Collectors.summingDouble(OrderDTO::getQuantity))));

        return priceQuantityMap;
    }
}
