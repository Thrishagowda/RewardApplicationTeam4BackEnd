package com.tcs.rewardapplicationsys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RedemptionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    @ElementCollection
    private List<String> itemsRedeemed;
    private Double pointsRedeemed;
    private LocalDateTime redemptionDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private CreditCard creditCard;

    public RedemptionHistory(String orderId, List<String> itemNames, Double requiredPoints, LocalDateTime now, String success) {
        this.orderId = orderId;
        this.itemsRedeemed = itemNames;
        this.pointsRedeemed = requiredPoints;
        this.redemptionDate = now;
        this.status = success;
    }
}
