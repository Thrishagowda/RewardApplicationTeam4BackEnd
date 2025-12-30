package com.tcs.rewardapplicationsys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Getter
@Setter
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardNumber")
    @JsonIgnore
    private CreditCard creditCard;
}
