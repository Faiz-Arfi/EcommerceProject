package com.projects.ecommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Services.CouponsCentralService;
import com.projects.ecommerce.Entity.CouponsCentral;

@RestController
public class CouponsCentralController {
    @Autowired
    public CouponsCentralService couponsCentralService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/couponcentral")
    public CouponsCentral saveCoupon(@RequestBody CouponsCentral couponsCentral){
        return couponsCentralService.saveCoupon(couponsCentral);
    }

    @GetMapping("/couponcentral")
    public List<CouponsCentral> getAllCoupons(){
        return couponsCentralService.getAllCoupons();
    }

    @GetMapping("/couponcentral/id/{id}")
    public CouponsCentral getCouponById(@PathVariable String id){
        return couponsCentralService.getCouponByCouponId(id);
    }

    @PutMapping("/couponcentral/id/{id}")
    public CouponsCentral updateCouponById(@RequestBody CouponsCentral coupon, @PathVariable String id){
        return couponsCentralService.updateCouponByCouponId(coupon, id);
    }

    @DeleteMapping("/couponcentral/id/{id}")
    public void deleteCouponById(@PathVariable String id){
        couponsCentralService.deleteCouponByCouponId(id);
    }

    
}
