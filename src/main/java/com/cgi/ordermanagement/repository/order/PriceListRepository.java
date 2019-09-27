package com.cgi.ordermanagement.repository.order;

import com.cgi.ordermanagement.model.order.PriceList;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PriceListRepository extends AuditBaseRepository<PriceList, Long> {

}
