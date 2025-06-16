package com.gopay.remittance.adapter.out.service.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership {
    // membership-service 에서 사용하는 도메인과 비슷하지만 동일한 클래스로 사용하지않음
    // 이 서비스에서만 사용하기 위함

    private String memberShipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;


}
