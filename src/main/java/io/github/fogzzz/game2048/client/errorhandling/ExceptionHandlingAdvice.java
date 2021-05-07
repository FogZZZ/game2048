package io.github.fogzzz.game2048.client.errorhandling;

import io.github.fogzzz.game2048.client.Controller;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

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
            controller.errorExit(e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            controller.errorExit(e.getMessage());
        }
        return result;
    }
}
