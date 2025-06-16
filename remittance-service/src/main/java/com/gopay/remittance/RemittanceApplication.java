package com.gopay.remittance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gopay")
public class RemittanceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RemittanceApplication.class, args);
  }
}
