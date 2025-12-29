package com.tcs.rewardapplicationsys.api;

import com.tcs.rewardapplicationsys.dto.RedemptionHistoryDTO;
import com.tcs.rewardapplicationsys.service.RedemptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerAPI {
    @Autowired
    RedemptionHistoryService redemptionHistoryService;

    @GetMapping("/customers/{customerId}/cards/{cardId}/redemptions/history")
    public ResponseEntity<List<RedemptionHistoryDTO>> getRedemptionHistory(
            @PathVariable Long customerId,
            @PathVariable Long cardId) {

        System.out.println("Fetching history for Card ID: " + cardId);
        var history = redemptionHistoryService.getHistoryByCard(cardId);
        return ResponseEntity.ok(history);
    }
}
