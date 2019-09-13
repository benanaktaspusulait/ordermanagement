package com.cgi.ordermanagement.resource.v1;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.error.GenericResponseDTO;
import com.cgi.ordermanagement.model.dto.order.CreateOrderDTO;
import com.cgi.ordermanagement.model.dto.order.OrderDTO;
import com.cgi.ordermanagement.model.dto.order.OrderStatusChangeDTO;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.service.OrderService;
import com.cgi.ordermanagement.util.Rights;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@Api(tags = "orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Create an order")
    @PostMapping
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> create(@RequestBody @Valid CreateOrderDTO dto) throws AppException {

        log.debug("REST funds to save OrderDTO: {}", dto);
        return new ResponseEntity<>(orderService.save(dto), HttpStatus.CREATED);
    }


    @RolesAllowed(value = {Rights.USER, Rights.ADMIN})
    @PatchMapping("/changeStatus")
    @ApiOperation(value = "Update Order Status to change with an ID", produces = "application/json", response = GenericResponseDTO.class)
    public ResponseEntity<?> changeOrderStatus(@RequestBody @Valid OrderStatusChangeDTO dto) throws AppException {
        orderService.updateOrderStatus(dto);
        return new ResponseEntity(new GenericResponseDTO(ErrorDTO.ResponseCodes.SUCCESS), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(value = "Get all orders ", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = OrderDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Access is denied")

    })
    @GetMapping("/liveOrders")
    public ResponseEntity<?> findAllLiveOrders() throws AppException {

        log.debug("REST request to get getAll countries");
        return new ResponseEntity<>(orderService.findByOrderStatus( OrderStatus.WAITING), HttpStatus.OK);
    }

    @ResponseBody
    @ApiOperation(value = "Search a order with an ID", response = OrderDTO.class)
    @GetMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> get(@PathVariable Long id) throws AppException, NotFoundException {

        log.debug("REST funds to get Order : {}", id);

        OrderDTO orderDTO = orderService.findById(id);
        return Optional.ofNullable(orderDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ApiOperation(value = "Update a order")
    @PatchMapping
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> update(@RequestBody @Valid OrderDTO orderDTO) throws AppException {

        log.debug("REST funds to update basket : {}", orderDTO);
        return new ResponseEntity<>(orderService.update(orderDTO), HttpStatus.OK);
    }
    
    @Timed
    @ApiOperation(value = "Get all orders ", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = OrderDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Access is denied")

    })
    @GetMapping
    public ResponseEntity<?> findAllOrders(Pageable pageable) throws AppException {

        log.debug("REST request to get getAll countries");
        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> delete(@PathVariable Long id) throws AppException {

        log.debug("REST funds to delete product : {}", id);
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
