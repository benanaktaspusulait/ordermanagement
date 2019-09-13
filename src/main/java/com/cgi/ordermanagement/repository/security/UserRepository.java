package com.cgi.ordermanagement.repository.security;

import com.cgi.ordermanagement.model.enums.DataStatus;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.model.security.UserType;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface UserRepository extends AuditBaseRepository<User, Long> {

     Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "userRoleList")
    User findOneByEmailIgnoreCase(String username);

    Page<User> findByUserType(Pageable pageable, UserType userType);

    Boolean existsByEmailAndDataStatus(String email, DataStatus dataStatus);

}
