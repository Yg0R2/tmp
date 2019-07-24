package com.yg0r2.tmp.resttemplate.thermos;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import net.jodah.failsafe.CircuitBreakerOpenException;

/**
 * Thermos related Exception provider.
 */
@Component
public class ThermosExceptionProvider {

    private static final List<String> CIRCUIT_BREAKER_MESSAGE_SNIPPETS = List.of("isCircuitBreakerOpen=true", "short-circuited");

    /**
     * Provides the cause of a {@link HystrixRuntimeException}, or the given {@link Throwable}.
     *
     * @param throwable throwable
     * @return with the cause or the same exception
     */
    public Throwable getOrCause(Throwable throwable) {
        Throwable result;

        if (/*(throwable instanceof UnrecoverableException) || */isHystrixRuntimeException(throwable)) {
            result = getOrCause(throwable.getCause());
        }
        else {
            result = throwable;
        }

        return result;
    }

    /**
     * Provide {@code true}, if the given {@link Throwable} is a "short circuited" Hystrix exception, {@code false} otherwise.
     *
     * @param throwable throwable
     * @return with {@code true}, if the given {@link Throwable} is a "short circuited" Hystrix exception, {@code false} otherwise
     */
    public boolean isCircuitBreakerOpen(Throwable throwable) {
        return (throwable instanceof CircuitBreakerOpenException) || isCircuitBreakerOpenExceptionMessage(throwable);
    }

    private boolean isHystrixRuntimeException(Throwable throwable) {
        return Optional.ofNullable(throwable)
            .filter(HystrixRuntimeException.class::isInstance)
            .map(Throwable::getCause)
            .map(Throwable::getMessage)
            .filter(Objects::nonNull)
            .isPresent();
    }

    private boolean isCircuitBreakerOpenExceptionMessage(Throwable throwable) {
        boolean isOpen = false;

        if (throwable instanceof HystrixRuntimeException) {
            if (isCircuitBreakerOpenExceptionMessage(throwable.getMessage())) {
                isOpen = true;
            }
            else {
                isOpen = isCircuitBreakerOpenExceptionMessage(throwable.getCause());
            }
        }

        return isOpen;
    }

    private boolean isCircuitBreakerOpenExceptionMessage(String message) {
        return (message != null) && CIRCUIT_BREAKER_MESSAGE_SNIPPETS.stream()
            .anyMatch(message::contains);
    }

}
