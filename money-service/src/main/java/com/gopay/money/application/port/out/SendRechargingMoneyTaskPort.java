package com.gopay.money.application.port.out;


import com.gopay.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    // 카프카 클러스터로 테스크를 async로 프로듀서하기 위한 포트

    void sendRechargingMoneyTask(RechargingMoneyTask task);

}
