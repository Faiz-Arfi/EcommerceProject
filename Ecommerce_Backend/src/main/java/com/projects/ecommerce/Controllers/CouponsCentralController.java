package com.projects.ecommerce.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.ecommerce.Services.CouponsCentralService;
import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CouponsCentralController {

    private final CouponsCentralService couponsCentralService;

    public CouponsCentralController(CouponsCentralService couponsCentralService) {
        this.couponsCentralService = couponsCentralService;
    }

    @PostMapping("/admin/couponcentral")
    public ResponseEntity<CouponsCentralDTO> saveCoupon(@RequestBody CouponsCentral couponsCentral,
                                                        UriComponentsBuilder uriBuilder){
        CouponsCentralDTO savedCouponsCentral = couponsCentralService.saveCoupon(couponsCentral);
        var location = uriBuilder.path("/couponcentral/{id}").buildAndExpand(savedCouponsCentral.getCouponId()).toUri();
        return ResponseEntity.created(location).body(savedCouponsCentral);
    }

    @GetMapping("/couponcentral")
    public Page<CouponsCentralDTO> getAllCoupons(Pageable p){
        couponsCentralService.validateSearchParamater(p);
        return couponsCentralService.getAllCouponsDTOPage(p);
    }

    @GetMapping("/couponcentral/{id}")
    public CouponsCentralDTO getCouponById(@PathVariable String id){
        return couponsCentralService.getCouponDTOByCouponId(id);
    }

    @PutMapping("/admin/couponcentral/{id}")
    public CouponsCentralDTO updateCouponById(@RequestBody CouponsCentral coupon, @PathVariable String id){
        return couponsCentralService.updateCouponByCouponId(coupon, id);
    }

    @DeleteMapping("/admin/couponcentral/{id}")
    public void deleteCouponById(@PathVariable String id){
        couponsCentralService.deleteCouponByCouponId(id);
    }
    
}
