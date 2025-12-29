package com.tcs.rewardapplicationsys.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private String transactionId;
    private String item;
    private Double amount;
    private LocalDateTime TransactionDate;
    private String status;
    private CreditCardDTO creditCard;
}
