package com.tcs.rewardapplicationsys.entity;

import com.tcs.rewardapplicationsys.dto.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CesUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
     private UserRole role;
}
