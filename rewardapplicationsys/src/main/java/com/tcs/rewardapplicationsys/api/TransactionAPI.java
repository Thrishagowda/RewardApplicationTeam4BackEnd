package com.tcs.rewardapplicationsys.api;

import com.tcs.rewardapplicationsys.dto.TransactionDTO;
import com.tcs.rewardapplicationsys.entity.Transaction;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionAPI {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/customers/{customerId}/cards/{cardNumber}/transactions")
    public ResponseEntity<List<Transaction>> loadTransactions(
            @PathVariable Long customerId,
            @PathVariable String cardNumber) throws RewardException {

        List<Transaction> transactions = transactionService.generateMockTransactionsForCard(cardNumber, 50);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/customers/{customerId}/cards/{cardId}/transactions/process")
    public ResponseEntity<Map<String, Object>> processTransactions(
            @PathVariable Long customerId,
            @PathVariable Long cardId,
            @RequestBody List<String> transactionIds) {

        System.out.println("Processing " + transactionIds.size() + " txns for Card " + cardId);

        // Delegate logic to service
        Map<String, Object> result = transactionService.processTransactions(transactionIds);

        return ResponseEntity.ok(result);
    }
}
