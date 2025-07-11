package com.projects.ecommerce.Entity.DTO;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private String UserId;
    private String firstName;
    private String lastName;
    private String email;
    private List<CouponsCentralDTO> couponsCentrals;
    private List<HomePageDealsDTO> homePageDeals;
    private BigDecimal balance;
    private String referralCode;
    private List<String> referredTo;
    private Boolean canRefer;
}
