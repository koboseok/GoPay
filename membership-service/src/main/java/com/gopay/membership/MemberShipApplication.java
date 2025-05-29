package com.gopay.membership;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 처음으로 비즈니스 로직을 마주하는 컨트롤러 http 로의 요청으로 첫 상호작용하는 외부에서 내부로 들어오는 어댑터
// 인바운드로 들어오는 웹 형식의 어댑터를 컨트롤러라고 간주할 수 있다.
@SpringBootApplication
public class MemberShipApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberShipApplication.class, args);
    }

}