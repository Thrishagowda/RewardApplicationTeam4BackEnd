package com.tcs.rewardapplicationsys.entity;
import com.tcs.rewardapplicationsys.entity.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cardNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transactionId")
    private List<Transaction> transaction;
    private Boolean isCardActive;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customerId")
    private Customer customer;
    private Double rewardPoints;
}
