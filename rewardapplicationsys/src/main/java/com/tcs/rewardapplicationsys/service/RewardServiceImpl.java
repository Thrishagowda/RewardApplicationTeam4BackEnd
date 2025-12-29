package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.RedemptionRequest;
import com.tcs.rewardapplicationsys.dto.RedemptionResponse;
import com.tcs.rewardapplicationsys.entity.CreditCard;
import com.tcs.rewardapplicationsys.entity.RedemptionHistory;
import com.tcs.rewardapplicationsys.entity.RewardItem;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CreditCardRepo;
import com.tcs.rewardapplicationsys.repository.RedemptionHistoryRepository;
import com.tcs.rewardapplicationsys.repository.RewardItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardItemRepository rewardItemRepository;

    @Autowired
    CreditCardRepo creditCardRepository;

    @Autowired
    RedemptionHistoryRepository redemptionHistoryRepository;

    @Override
    public List<RewardItem> getAllRewards() {
        return rewardItemRepository.findAll();
    }

//    @Override
//    public List<RewardItem> getRewardsByCategory(String category) {
//        return rewardItemRepository.findByCategory(category);
//    }

    @Override
    public RedemptionResponse redeemPoints(String cardNumber, RedemptionRequest request) throws RewardException {
        try {
            // 1. Fetch Card (now includes version)
            CreditCard card = creditCardRepository.findByCardNumber(cardNumber);
            if(card == null){
                throw new RewardException("Credit Card not found with ID: " + cardNumber);
            }

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

            RedemptionHistory history = new RedemptionHistory();

            history.setOrderId(orderId);
            history.setItemsRedeemed(request.getItemNames());
            history.setPointsRedeemed(requiredPoints);
            history.setRedemptionDate(LocalDateTime.now());
            history.setStatus("SUCCESS");
            history.setCreditCard(card);

            redemptionHistoryRepository.save(history);

            return new RedemptionResponse("SUCCESS", orderId, requiredPoints, card.getRewardPoints());

        } catch (OptimisticLockingFailureException e) {
            // Handle the Race Condition gracefully
            throw new RewardException("Transaction conflict! Please try redeeming again.");
        }
    }
}
