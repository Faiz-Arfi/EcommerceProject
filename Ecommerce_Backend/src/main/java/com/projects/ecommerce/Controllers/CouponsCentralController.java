package com.projects.ecommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Services.CouponsCentralService;
import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;

@RestController
public class CouponsCentralController {
    @Autowired
    public CouponsCentralService couponsCentralService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/couponcentral")
    public CouponsCentralDTO saveCoupon(@RequestBody CouponsCentral couponsCentral){
        try {
            return couponsCentralService.saveCoupon(couponsCentral);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    @GetMapping("/couponcentral")
    public Page<CouponsCentralDTO> getAllCoupons(Pageable p){
        try {
            return couponsCentralService.getAllCouponsDTOPage(p);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    @GetMapping("/couponcentral/id/{id}")
    public CouponsCentralDTO getCouponById(@PathVariable String id){
        try {
            return couponsCentralService.getCouponDTOByCouponId(id);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    @PutMapping("/couponcentral/id/{id}")
    public CouponsCentralDTO updateCouponById(@RequestBody CouponsCentral coupon, @PathVariable String id){
        try {
            return couponsCentralService.updateCouponByCouponId(coupon, id);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    @DeleteMapping("/couponcentral/id/{id}")
    public void deleteCouponById(@PathVariable String id){
        try {
            couponsCentralService.deleteCouponByCouponId(id);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }
    
}
