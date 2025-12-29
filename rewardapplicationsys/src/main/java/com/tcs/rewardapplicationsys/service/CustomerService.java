package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.CustomerDTO;
import com.tcs.rewardapplicationsys.entity.Customer;
import com.tcs.rewardapplicationsys.exception.RewardException;

import java.util.List;

public interface CustomerService {
void addCustomer(CustomerDTO customer) throws RewardException;
Integer deleteCustomer(Integer custId) throws RewardException;
    public List<Customer> GetCustomerBycustId(Integer custId) throws RewardException;
    public List<Customer> getCustomerByCardNum(String creditNumber) throws RewardException ;
    public List<Customer> getAllCustomer() throws RewardException ;
    }
