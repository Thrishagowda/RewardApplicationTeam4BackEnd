package com.tcs.rewardapplicationsys.repository;

import com.tcs.rewardapplicationsys.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    public List<Customer> findByCreditCardCardNumber(String cardNumber);
}