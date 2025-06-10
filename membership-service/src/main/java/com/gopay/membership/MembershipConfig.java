package com.gopay.membership;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({
        @ComponentScan(basePackages = "com.gopay.common"),
        @ComponentScan(basePackages = "com.gopay.loggingconsumer")
})

public class MembershipConfig {
}
