package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.CustomerDTO;
import com.tcs.rewardapplicationsys.dto.CustomerType;
import com.tcs.rewardapplicationsys.entity.Customer;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepo  customerRepo;
    @Override
    public Integer addCustomer(CustomerDTO customer) throws RewardException {
        // 1. Only check for existing customer if an ID is actually provided
        if (customer.getCustomerId() != null) {
            Optional<Customer> customer1 = customerRepo.findById(customer.getCustomerId());
            if (customer1.isPresent()) {
                throw new RewardException("Customer already exists");
            }
        }

        // 2. Proceed with creating the new customer
        Customer cust = new Customer();
        cust.setDoj(customer.getDoj());
        cust.setFirstName(customer.getFirstName());
        cust.setLastName(customer.getLastName());

        // 3. Logic for Premium/Regular status (Threshold: Dec 29, 2022)
        if (cust.getDoj() != null && cust.getDoj().isBefore(LocalDate.now().minusYears(3))) {
            cust.setCustomerType(CustomerType.PREMIUM);
        } else {
            cust.setCustomerType(CustomerType.REGULAR);
        }

        cust.setIsActive(true);
        customerRepo.save(cust);
        return cust.getCustomerId();
    }


    @Override
    public Integer deleteCustomer(Integer custId) throws RewardException {
        Optional<Customer> customer1=customerRepo.findById(custId);
        if(customer1.isEmpty()){
            throw new RewardException("Customer does not exist");
        }
        else{
            customer1.get().setIsActive(false);
            customerRepo.save(customer1.get());
        }
        return customer1.get().getCustomerId();
    }

    @Override
    public List<Customer> GetCustomerBycustId(Integer custId) throws RewardException {
        Optional<Customer> cust=customerRepo.findById(custId);

        if(cust.isEmpty()){
            throw new RewardException("Customer Not Found");
        }
        else{
            return Collections.singletonList(cust.get());

        }
    }

    @Override
    public List<Customer> getCustomerByCardNum(String cardNumber) throws RewardException {
        List<Customer> customer = customerRepo.findByCreditCardCardNumber(cardNumber);
        if(customer.isEmpty()){
            throw new RewardException("Customer Not Found");
        }
        else{
            return customer;
        }
    }

    @Override
    public List<Customer> getAllCustomer() throws RewardException {
        List<Customer> customer=customerRepo.findAll();
        if(customer.isEmpty()){
            throw new RewardException("Customer Not Found");
        }
        return customer;
    }


}
