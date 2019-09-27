package com.cgi.ordermanagement.repository.order;

import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.model.order.Order;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends AuditBaseRepository<Order, Long> {


}
