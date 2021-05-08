package io.github.fogzzz.game2048.client.errorhandling;

import io.github.fogzzz.game2048.client.Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandlingAdvice {

    private final Controller controller;

    @Around("@within(HandleException) || @annotation(HandleException)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage(), e);
            if (joinPoint.getSignature().getName().equals("loginUser")
                    && e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                return null;
            } else {
                controller.errorExit(e.getMessage());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            controller.errorExit(e.getMessage());
        }
        return result;
    }
}
