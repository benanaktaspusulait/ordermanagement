package com.cgi.ordermanagement.repository.security;

import com.cgi.ordermanagement.model.security.UserRole;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRoleRepository extends AuditBaseRepository<UserRole, Long> {

}