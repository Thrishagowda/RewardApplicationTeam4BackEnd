package com.tcs.rewardapplicationsys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    private String transactionId;
    private String item;
    private Double amount;
    private LocalDateTime TransactionDate;
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardNumber")
    private CreditCard creditCard;
}
