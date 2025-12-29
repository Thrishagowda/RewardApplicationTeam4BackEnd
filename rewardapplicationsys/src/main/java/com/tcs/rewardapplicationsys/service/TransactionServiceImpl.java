package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.entity.CreditCard;
import com.tcs.rewardapplicationsys.entity.Customer;
import com.tcs.rewardapplicationsys.entity.Transaction;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CreditCardRepo;
import com.tcs.rewardapplicationsys.repository.CustomerRepo;
import com.tcs.rewardapplicationsys.repository.TransactionRepository;
import com.tcs.rewardapplicationsys.utility.DataHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    CreditCardRepo creditCardRepository;

    @Autowired
    CustomerRepo cust;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> generateMockTransactionsForCard(String cardNumber, int count) throws RewardException {

       // 1. Fetch the actual Credit Card entity from DB
        CreditCard card = creditCardRepository.findByCardNumber(cardNumber);
        if(card == null){
            throw new RewardException("Credit Card not found with ID: " + cardNumber);
        }

        List<Transaction> mockTransactions = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            Transaction txn = new Transaction();

            // 2. Set the ID
            txn.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            // 3. Set Item Name
            txn.setItem("Item " + i);
            // 4. Set Random Amount (500 - 50,000)
            txn.setAmount(DataHelper.generateRandomAmount(500, 50000).doubleValue());
            // 5. Set Random Date
            txn.setTransactionDate(DataHelper.generateRandomPastDate(30));
            // 6. Set Status
            txn.setStatus("PENDING");
            // 7. Link the specific Credit Card Entity
            txn.setCreditCard(card);
            mockTransactions.add(txn);

        }
        return transactionRepository.saveAll(mockTransactions);
    }

    @Override
    public Map<String, Object> processTransactions(List<String> transactionId) {
        List<Transaction> transactions = transactionRepository.findAllById(transactionId);

        double totalPointsAdded = 0;
        int processedCount = 0;

        for (Transaction txn : transactions) {
            // Only process if currently PENDING
            if ("PENDING".equalsIgnoreCase(txn.getStatus())) {

                CreditCard card = txn.getCreditCard();
                Customer customer = cust.findByCreditCardCardNumber(card.getCardNumber()); // Assuming correct mapping

                // 1. Determine Customer Type (Premium Logic)
                boolean isPremium = DataHelper.isPremiumCustomer(customer.getDoj()); // DOJ = Date of Joining

                // 2. Calculate Rate (10% for Premium, 5% for Regular)
                double rate = isPremium ? 0.10 : 0.05;

                // 3. Calculate Points
                double points = txn.getAmount() * rate;

                // 4. Update Card Reward Points
                double currentPoints = card.getRewardPoints() != null ? card.getRewardPoints() : 0.0;
                card.setRewardPoints(currentPoints + points);

                // 5. Update Transaction Status
                txn.setStatus("PROCESSED");

                // Save changes
                creditCardRepository.save(card);
                transactionRepository.save(txn);

                totalPointsAdded += points;
                processedCount++;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("processedCount", processedCount);
        response.put("totalPointsAdded", Math.floor(totalPointsAdded));

        return response;
    }


}
