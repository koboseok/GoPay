package com.gopay.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UseCase {
    // 인터페이스 아키텍쳐
    // 논리적으로 정의
    // 단순히 UseCase 의미하는 어노테이션
    // 서비스에서 이용
    // 사용을 하는 예시
    @AliasFor(annotation = Component.class)
    String value() default "";
}
