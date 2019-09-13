package com.cgi.ordermanagement.handler;


import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.error.GenericResponseDTO;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import com.cgi.ordermanagement.model.dto.system.SystemErrorDTO;
import com.cgi.ordermanagement.service.system.SystemErrorService;
import com.cgi.ordermanagement.service.SecurityUtils;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private SystemErrorService systemErrorService;

    private ResponseEntity<Object> buildResponseEntity(Integer errorCode) {
        return new ResponseEntity(new GenericResponseDTO(errorCode), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(Integer errorCode, String msg) {
        return new ResponseEntity(new GenericResponseDTO(msg, errorCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleAllExceptions(Exception ex) {
        systemErrorService.save(new SystemErrorDTO(ErrorDTO.ResponseCodes.GENERAL, SecurityUtils.getCurrentLogin(),
                ex));
        return buildResponseEntity(ErrorDTO.ResponseCodes.GENERAL, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<?> handleAllExceptions(AccessDeniedException ex) {
        return new ResponseEntity(new GenericResponseDTO(ex.getMessage(), 100), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleAllExceptions(NotFoundException ex) {
        return buildResponseEntity(ErrorDTO.ResponseCodes.NOT_FOUND_GENERAL, ex.getMessage());
    }

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<?> handleAppExceptions(AppException ex) {
        systemErrorService.save(new SystemErrorDTO(ex.getErrorCode(), SecurityUtils.getCurrentLogin(),
                ex));
        logger.error(ex.getMessage());
        return buildResponseEntity(ex.getErrorCode(), ex.getErrorDescription());
    }

}
