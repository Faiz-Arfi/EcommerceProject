package com.projects.ecommerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.CouponsCentral;

@Repository
public interface CouponsCentralRepo extends JpaRepository<CouponsCentral, String>{
    Optional<CouponsCentral> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);

    void deleteByCouponCode(String couponCode);
}
