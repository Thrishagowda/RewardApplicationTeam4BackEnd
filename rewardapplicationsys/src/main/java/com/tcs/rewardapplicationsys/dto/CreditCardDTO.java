package com.tcs.rewardapplicationsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {
    private Integer cardId;
    private String cardNumber;
    private TransactionDTO transaction;
    private Boolean isCardActive;
    private CustomerDTO  customer;
}
