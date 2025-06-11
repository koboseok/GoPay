package com.gopay.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyTask { // Increase Money
    // 머니를 충전하기 위함

    private String taskId;
    private String taskName;
    private String membershipId;
    private List<SubTask> subTaskList;

    // 페이(법인) 계좌
    private String toBankName;
    // 페이(법인) 계좌 번호
    private String toBankAccountNumber;
    private int moneyAmount; // only won

}
