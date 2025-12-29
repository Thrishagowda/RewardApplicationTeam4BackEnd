package com.tcs.rewardapplicationsys.entity;
import com.tcs.rewardapplicationsys.entity.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
    @Column(unique = true, nullable = false)
    private String cardNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id_fk")
    private List<Transaction> transaction;
    private Boolean isCardActive;
    private Double rewardPoints;
    @Version
    private Long version;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id_fk") // This is the column name in the DB
    private List<RedemptionHistory> redemptionHistory = new ArrayList<>();

}
