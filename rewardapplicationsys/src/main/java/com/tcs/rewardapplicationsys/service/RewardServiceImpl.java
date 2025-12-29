package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.RedemptionRequest;
import com.tcs.rewardapplicationsys.dto.RedemptionResponse;
import com.tcs.rewardapplicationsys.entity.CreditCard;
import com.tcs.rewardapplicationsys.entity.RewardItem;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CreditCardRepository;
import com.tcs.rewardapplicationsys.repository.RewardItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardItemRepository rewardItemRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public List<RewardItem> getAllRewards() {
        return rewardItemRepository.findAll();
    }

//    @Override
//    public List<RewardItem> getRewardsByCategory(String category) {
//        return rewardItemRepository.findByCategory(category);
//    }

    public RedemptionResponse redeemPoints(Long cardId, RedemptionRequest request) throws RewardException {
        try {
            // 1. Fetch Card (now includes version)
            CreditCard card = creditCardRepository.findById(cardId)
                    .orElseThrow(() -> new RewardException("Card not found"));

            Double currentBalance = card.getRewardPoints() != null ? card.getRewardPoints() : 0.0;
            Double requiredPoints = request.getTotalPoints();

            // 2. Logic Check
            if (currentBalance < requiredPoints) {
                throw new RewardException("Insufficient Balance!");
            }

            // 3. Deduct Points
            card.setRewardPoints(currentBalance - requiredPoints);

            // 4. Save Card
            creditCardRepository.save(card);

            // 5. Generate History & Vouchers (Only happens if Step 4 succeeds)
            String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

//            RedemptionHistory history = new RedemptionHistory(orderId, card, requiredPoints, String.join(", ", request.getItemNames()));
//            historyRepository.save(history);

            return new RedemptionResponse("SUCCESS", orderId, requiredPoints, card.getRewardPoints());

        } catch (OptimisticLockingFailureException | RewardException e) {
            // Handle the Race Condition gracefully
            throw new RewardException("Transaction conflict! Please try redeeming again.");
        }
    }
}
