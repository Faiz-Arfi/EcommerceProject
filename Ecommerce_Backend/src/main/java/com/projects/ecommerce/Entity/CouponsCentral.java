package com.projects.ecommerce.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "coupons_central")
@Getter
@Setter
public class CouponsCentral {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, unique = true)
    private String couponCode;
    @Column(nullable = false)
    private String heading;
    @Column(nullable = false)
    private String description;
}
