package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.entity.RewardItem;

import java.util.List;

public interface RewardService {
    List<RewardItem> getAllRewards();

//    List<RewardItem> getRewardsByCategory(String category);
}
