package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.CustomerDTO;
import com.tcs.rewardapplicationsys.entity.Customer;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepo  customerRepo;
    @Override
    public void addCustomer(CustomerDTO customer) throws RewardException {

        Optional<Customer> customer1=customerRepo.findById(customer.getCustomerId());
        Customer cust=new Customer();
        if(customer1.isEmpty()){
            cust.setDoj(customer.getDoj());
            cust.setFirstName(customer.getFirstName());
            cust.setLastName(customer.getLastName());
            cust.setIsActive(true);
            customerRepo.save(cust);
        }
        else{
                throw new RewardException("Customer already exists");
        }
    }

    @Override
    public Integer deleteCustomer(Integer custId) throws RewardException {
        Optional<Customer> customer1=customerRepo.findById(custId);
        if(customer1.isEmpty()){
            throw new RewardException("Customer does not exist");
        }
        else{
            customer1.get().setIsActive(false);
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
    public List<Customer> getCustomerByCardNum(String creditNumber) throws RewardException {
        List<Customer> customer=customerRepo.findByCardNumber(creditNumber);
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
