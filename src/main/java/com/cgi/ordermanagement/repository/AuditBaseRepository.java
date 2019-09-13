package com.cgi.ordermanagement.repository;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.audit.AuditBase;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;


@NoRepositoryBean
public interface AuditBaseRepository<T extends AuditBase, ID extends Serializable> extends JpaRepository<T, ID> {

    Logger log = LoggerFactory.getLogger(AuditBaseRepository.class);

    default T findByIdT(ID id) throws AppException {

        String typeName = ((ParameterizedType) ((Class) getClass().getGenericInterfaces()[0]).getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
        log.debug("Request to get {} : {}", typeName, id);

        Optional<T> result = this.findById(id);
        if (!result.isPresent()) {
            throw new AppException(ErrorDTO.ResponseCodes.NOT_FOUND_GENERAL,typeName, id.toString());
        }
        return result.get();
    }




}