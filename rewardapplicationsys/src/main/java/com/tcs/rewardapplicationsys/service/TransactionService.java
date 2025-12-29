package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.entity.Transaction;
import com.tcs.rewardapplicationsys.exception.RewardException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> generateMockTransactionsForCard(Long cardId, int count) throws RewardException;

    Map<String, Object> processTransactions(List<String> transactionIds);
}
