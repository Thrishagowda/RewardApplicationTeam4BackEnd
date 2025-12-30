package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.CesUserDTO;
import com.tcs.rewardapplicationsys.exception.RewardException;

public interface CesService {
    public void addCesUser(CesUserDTO dto);
    public String deleteByUserName(String username) throws RewardException;


}
