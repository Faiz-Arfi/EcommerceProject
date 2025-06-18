package com.projects.ecommerce.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponsCentralDTO {
    private String couponId;
    private String couponCode;
    private String heading;
    private String description;
    private String category;
}
