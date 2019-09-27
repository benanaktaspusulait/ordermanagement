package com.cgi.ordermanagement.repository.order;

import com.cgi.ordermanagement.model.order.Order;
import com.cgi.ordermanagement.model.project.Project;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ProjectRepository extends AuditBaseRepository<Project, Long> {


}
