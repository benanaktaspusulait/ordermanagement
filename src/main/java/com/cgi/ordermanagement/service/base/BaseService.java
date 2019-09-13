package com.cgi.ordermanagement.service.base;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.AuditBaseDTO;
import com.cgi.ordermanagement.repository.AuditBaseRepository;
import com.cgi.ordermanagement.config.StaticApplicationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Service
public  class BaseService<T extends AuditBaseDTO,R extends AuditBaseRepository>  {

    @Autowired
    ModelMapper modelMapper;


    public T findById(Long id) throws AppException {
        R repository= (R) StaticApplicationContext.getContext().getBean(getRGenericTypeClass());
        return modelMapper.map(repository.findByIdT(id), (Type) getGenericTypeClass());
    }


    @SuppressWarnings("unchecked")
    private Class<T> getGenericTypeClass() {
        try {
            Class<?> clazz = getClassType(0);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    private Class<?> getClassType(int i) throws ClassNotFoundException {
        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[i].getTypeName();
        return Class.forName(className);
    }

    @SuppressWarnings("unchecked")
    private Class<R> getRGenericTypeClass() {
        try {
            Class<?> clazz = getClassType(1);
            return (Class<R>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
