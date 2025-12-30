package com.tcs.rewardapplicationsys.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcs.rewardapplicationsys.dto.CustomerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String firstName;
    private String lastName;
    private LocalDate doj;
    private String email;
    private Long phoneNum;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cust_id_fk")
    @JsonManagedReference
    private List<CreditCard> creditCard;
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    @Column(name = "is_active")
    private Boolean isActive;
}
