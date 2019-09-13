package com.cgi.ordermanagement.repository.security;

import com.cgi.ordermanagement.model.enums.RoleName;
import com.cgi.ordermanagement.model.security.Role;
import lombok.val;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    val findByIdAndNameNotContaining(Long id, String name);
}
