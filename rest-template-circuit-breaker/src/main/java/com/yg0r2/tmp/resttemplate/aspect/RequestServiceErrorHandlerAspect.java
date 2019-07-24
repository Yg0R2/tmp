package com.yg0r2.tmp.resttemplate.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.tmp.resttemplate.thermos.ThermosExceptionProvider;

@Aspect
@Component
public class RequestServiceErrorHandlerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestServiceErrorHandlerAspect.class);

    @Autowired
    private ThermosExceptionProvider thermosExceptionProvider;

    @Around("execution(* com.yg0r2.tmp.resttemplate.service.RequestService.callService())")
    public String executeDefendedMethod(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return (String) proceedingJoinPoint.proceed();
        }
        catch (Throwable throwable) {
            LOGGER.error("Is circuit open: {}", thermosExceptionProvider.isCircuitBreakerOpen(throwable));

            LOGGER.error("Exception:", thermosExceptionProvider.getOrCause(throwable));

            //LOGGER.error("Catch error in Aspect", throwable);
        }

        return "handled in aspect";
        //throw new RuntimeException("Throw error from Aspect");
    }

}
