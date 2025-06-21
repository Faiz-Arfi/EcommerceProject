package com.projects.ecommerce.Controllers;

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

import com.projects.ecommerce.Services.CouponsCentralService;
import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;

@RestController
public class CouponsCentralController {

    private final CouponsCentralService couponsCentralService;

    public CouponsCentralController(CouponsCentralService couponsCentralService) {
        this.couponsCentralService = couponsCentralService;
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/couponcentral")
    public CouponsCentralDTO saveCoupon(@RequestBody CouponsCentral couponsCentral){
        return couponsCentralService.saveCoupon(couponsCentral);
    }

    @GetMapping("/couponcentral")
    public Page<CouponsCentralDTO> getAllCoupons(Pageable p){
        couponsCentralService.validateSearchParamater(p);
        return couponsCentralService.getAllCouponsDTOPage(p);
    }

    @GetMapping("/couponcentral/id/{id}")
    public CouponsCentralDTO getCouponById(@PathVariable String id){
        return couponsCentralService.getCouponDTOByCouponId(id);
    }

    @PutMapping("/couponcentral/id/{id}")
    public CouponsCentralDTO updateCouponById(@RequestBody CouponsCentral coupon, @PathVariable String id){
        return couponsCentralService.updateCouponByCouponId(coupon, id);
    }

    @DeleteMapping("/couponcentral/id/{id}")
    public void deleteCouponById(@PathVariable String id){
        couponsCentralService.deleteCouponByCouponId(id);
    }
    
}
