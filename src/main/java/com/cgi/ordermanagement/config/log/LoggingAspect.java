package com.cgi.ordermanagement.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repositories Spring components.
 */
@Slf4j
@Aspect
@Configuration
public class LoggingAspect {

    /**
     * Instantiates a new logging aspect.
     */
    public LoggingAspect() {
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.cgi.ordermanagement..*)")
    public void applicationPackagePointcut() {
      log.debug("test");
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        log.debug("test");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        boolean checkShouldWriteLog = false;
        if (joinPoint != null && joinPoint.getSignature() != null && log.isDebugEnabled()) {
            checkShouldWriteLog = true;
        }
        if (checkShouldWriteLog) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }

        Object result = null;
        if (joinPoint != null) {
            result = joinPoint.proceed();
        }
        if (checkShouldWriteLog) {
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
        }
        return result;

    }
}