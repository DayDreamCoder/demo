package com.minliu.demo.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author minliu
 */
@Component
@Aspect
@Order(-1)
public class LogAopUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogAopUtil.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切点
     */
    @Pointcut("execution(public * com.minliu.demo.service..*.*(..))")
    public void pointCut() {
        //
    }

    /**
     * 执行方法前记录日志
     *
     * @param joinPoint 连接点
     */
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        logger.info("------LOGGING START------");
        //获取当前request对象
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        //记录request信息
        logger.info("URL:{}", request.getRequestURL());
        logger.info("HTTP_METHOD:{}", request.getMethod());
        logger.info("IP:{}", request.getRemoteAddr());
        Signature signature = joinPoint.getSignature();
        logger.info("CLASS_METHOD:{}", signature.getDeclaringTypeName() + "." + signature.getName());
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            logger.info("{}:{}", parameterName, request.getParameter(parameterName));
        }
    }

    @After("pointCut()")
    public void doAfter() {
        logger.info("------LOGGING END------");
    }

    @AfterReturning("pointCut()")
    public void doAfterReturning() {
        logger.info("LogAopUtil.doAfterReturning()");
        if (startTime != null && startTime.get() != null) {
            logger.info("costs {}ms", System.currentTimeMillis() - startTime.get());
            startTime.remove();
        }
    }

}
