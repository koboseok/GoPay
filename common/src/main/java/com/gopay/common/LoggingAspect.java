package com.gopay.common;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Aspect
@Component
public class LoggingAspect {
    // aop
    // 특정 목적을 가지고 공통으로 처리할 수 있는 기술

    private final LoggingProducer loggingProducer;

    public LoggingAspect(LoggingProducer loggingProducer) {this.loggingProducer = loggingProducer;}


    @Before("execution(* com.gopay.*.adapter.in.web.*.*(..))")
    // 실제 비지니스로직 수행 이전에 위에 선언된 패키지 안에 있는 메서드들이 호출될때 먼저 실행되는 코드블럭
    public void beforeMethodExecution(@NotNull JoinPoint joinPoint) {

        System.out.println("@@@@@ 타냐타냐타냐 ?!");
        String methodName = joinPoint.getSignature().getName();

        loggingProducer.sendMessage("logging", "Before executing method: " + methodName);

        // produce access log


    }
}
