package com.projects.ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Repository.CouponsCentralRepo;

@Service
public class CouponsCentralService {

    @Autowired
    public CouponsCentralRepo couponsCentralRepo;

    public CouponsCentral saveCoupon(CouponsCentral couponsCentral){
        if(couponsCentralRepo.existsByCouponCode(couponsCentral.getCouponCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon code already exists");
        }
        if(couponsCentral.getCouponCode() == null || couponsCentral.getCouponCode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon code cannot be null or empty");
        }
        else if(couponsCentral.getHeading() == null || couponsCentral.getHeading().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Heading cannot be null or empty");
        }
        else if(couponsCentral.getDescription() == null || couponsCentral.getDescription().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description cannot be null or empty");
        }
        return couponsCentralRepo.save(couponsCentral);
    }

    public List<CouponsCentral> getAllCoupons(){
        return couponsCentralRepo.findAll();
    }

    public CouponsCentral getCouponByCouponId(Long id){
        return couponsCentralRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found with id: "+id));

    }

    public CouponsCentral updateCouponByCouponId(CouponsCentral newCoupon, Long id){
        CouponsCentral existingCoupon = couponsCentralRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found with id: "+ id));

        updateCouponByCheckingFields(existingCoupon, newCoupon);

        return couponsCentralRepo.save(existingCoupon);
    }

    private void updateCouponByCheckingFields(CouponsCentral existingCoupon, CouponsCentral newCoupon){
        if(newCoupon.getDescription() != null){
            existingCoupon.setDescription(newCoupon.getDescription());
        }
        if(newCoupon.getHeading() != null){
            existingCoupon.setHeading(newCoupon.getHeading());
        }
        couponsCentralRepo.save(existingCoupon);
    }

    public void deleteCouponByCouponId(Long id){
        if(!couponsCentralRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found with id: "+ id);
        }
        couponsCentralRepo.deleteById(id);
    }
}
