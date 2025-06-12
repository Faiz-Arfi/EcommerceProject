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
    public void saveCoupon(@RequestBody CouponsCentral couponsCentral){
        couponsCentralService.saveCoupon(couponsCentral);
    }

    @GetMapping("/couponcentral")
    public List<CouponsCentral> getAllCoupons(){
        return couponsCentralService.getAllCoupons();
    }

    @GetMapping("/couponcentral/id/{id}")
    public CouponsCentral getCouponById(@PathVariable Long id){
        return couponsCentralService.getCouponByCouponId(id);
    }

    @GetMapping("/couponcentral/code/{couponCode}")
    public CouponsCentral getCouponByCouponCode(@PathVariable String couponCode){
        return couponsCentralService.getCouponByCouponCode(couponCode);
    }

    @PutMapping("/couponcentral/id/{id}")
    public void updateCouponById(@RequestBody CouponsCentral coupon, @PathVariable Long id){
        couponsCentralService.updateCouponByCouponId(coupon, id);
    }

    @PutMapping("/couponcentral/code/{couponCode}")
    public void updateCouponByCouponCode(@RequestBody CouponsCentral coupon, @PathVariable String couponCode){
        couponsCentralService.updateCouponByCouponCode(coupon, couponCode);
    }

    @DeleteMapping("/couponcentral/id/{id}")
    public void deleteCouponById(@PathVariable Long id){
        couponsCentralService.deleteCouponByCouponId(id);
    }

    @DeleteMapping("/couponcentral/code/{couponCode}")
    public void deleteCouponByCouponCode(@PathVariable String couponCode){
        couponsCentralService.deleteCouponByCouponCode(couponCode);
    }

    
}
