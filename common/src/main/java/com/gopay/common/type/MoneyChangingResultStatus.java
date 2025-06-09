package com.gopay.common.type;

public enum MoneyChangingResultStatus {
    REQUESTED, // 요쳥
    SUCCEEDED, // 성공
    FAILED, // 실패
    CANCELLED, // 취소
    FAILED_NOT_ENOUGH_MONEY,// 실패 - 잔액 부족
    FAILED_NOT_EXIST_MEMBERSHIP, // 실패 - 맴버십 없음
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST, // 실패 - 머니 변액 요청 없음
}
