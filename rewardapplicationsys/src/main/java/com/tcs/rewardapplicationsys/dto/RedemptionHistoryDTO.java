package com.tcs.rewardapplicationsys.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RedemptionHistoryDTO {
    private String orderId;
    private String itemsRedeemed;
    private Double pointsRedeemed;
    private LocalDateTime redemptionDate;
    private String status;

    public RedemptionHistoryDTO(String orderId, String items, Double points, LocalDateTime date, String status) {
        this.orderId = orderId;
        this.itemsRedeemed = items;
        this.pointsRedeemed = points;
        this.redemptionDate = date;
        this.status = status;
    }
}
