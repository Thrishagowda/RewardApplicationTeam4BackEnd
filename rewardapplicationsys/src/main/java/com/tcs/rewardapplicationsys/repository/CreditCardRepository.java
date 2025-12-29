package com.tcs.rewardapplicationsys.repository;

import com.tcs.rewardapplicationsys.entity.CreditCard;
import com.tcs.rewardapplicationsys.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    Customer findByCreditCardCardNumber(String cardNumber);
}
