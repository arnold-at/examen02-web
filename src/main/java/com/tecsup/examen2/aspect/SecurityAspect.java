package com.tecsup.examen2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Before("execution(* com.tecsup.examen2..*Controller.delete*(..)) || " +
            "execution(* com.tecsup.examen2..*Controller.update*(..))")
    public void auditCriticalOperation(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.warn("OPERACIÓN CRÍTICA: {} desde IP: {}",
                    joinPoint.getSignature().getName(),
                    request.getRemoteAddr());
        }
    }
}