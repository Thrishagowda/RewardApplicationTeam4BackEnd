package com.tcs.rewardapplicationsys.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedemptionHistory {
    private String orderId;
    private String itemsRedeemed;
    private Double pointsRedeemed;
    private LocalDateTime redemptionDate;
    private String status;
}
