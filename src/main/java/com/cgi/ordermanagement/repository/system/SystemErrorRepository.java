package com.cgi.ordermanagement.repository.system;

import com.cgi.ordermanagement.model.system.SystemError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemErrorRepository extends JpaRepository<SystemError, Long> {
}
