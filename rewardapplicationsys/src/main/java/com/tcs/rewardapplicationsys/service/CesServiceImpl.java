package com.tcs.rewardapplicationsys.service;

import com.tcs.rewardapplicationsys.dto.CesUserDTO;
import com.tcs.rewardapplicationsys.dto.UserRole;
import com.tcs.rewardapplicationsys.entity.CesUser;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.repository.CesUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CesServiceImpl implements CesService {
    // Logic: Admin adding a CES User
    @Autowired
    CesUserRepo userRepo;
    public void addCesUser(CesUserDTO dto) {
        CesUser user = new CesUser();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        // Using the Enum ensures only valid roles can be assigned
        user.setRole(UserRole.CES_USER);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public String deleteByUserName(String username) throws RewardException {
        // 1. Fetch the user
        CesUser user = userRepo.findByUserName(username);

        // 2. Logic Check: Must exist and must NOT be an Admin
        if (user != null) {
            if (user.getRole() == UserRole.CES_USER) {
                userRepo.delete(user);
                return "User " + username + " deleted successfully.";
            } else {
                throw new RewardException("Administrator accounts cannot be deleted.");
            }
        } else {
            throw new RewardException("User not found with username: " + username);
        }
    }


}



