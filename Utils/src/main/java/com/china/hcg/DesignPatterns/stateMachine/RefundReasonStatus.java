package com.china.hcg.DesignPatterns.stateMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
    public enum RefundReasonStatus  {
    READY_TO_APPROVE(1, "待审批"),
    APPROVED(2, "审核通过"),
    REJECT(3, "审核拒绝"),
    AUTO_REJECT(4, "超时自动拒绝"),
    ;
    private final int value;
    @Getter
    private final String msg;
    public static RefundReasonStatus valueOf(int val) {
        Optional<RefundReasonStatus> optional = Arrays.stream(values())
                .filter(instance -> instance.value == val).limit(1)
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new IllegalArgumentException("未知退款理由审批状态类型: " + val);
    }
}

