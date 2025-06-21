package com.projects.ecommerce.Controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Entity.DTO.UsersDTO;
import com.projects.ecommerce.Services.UsersService;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody Users user, UriComponentsBuilder uriBuilder){
        UsersDTO savedUser = usersService.saveUser(user);
        var location = uriBuilder.path("/users/{userId}").buildAndExpand(savedUser.getUserId()).toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping("/users")
    public Page<UsersDTO> getAllUsers(Pageable p){
        return usersService.getAllUsersDTO(p);
    }

    @GetMapping("/users/{userId}")
    public UsersDTO getUserById(@PathVariable String userId) {
        System.out.println("User ID: " + userId);
        return usersService.getUserDTOById(userId);
    }

    @PostMapping("/users/coupons")
    public ResponseEntity<UsersDTO> setUserCoupon(
            @RequestParam String userId,
            @RequestParam String couponId,
            UriComponentsBuilder uriBuilder) {
        var location = uriBuilder.path("/users/coupons/{userId}").buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).body(usersService.setCouponsForUserId(userId, couponId));
    }

    @PostMapping("/users/deals")
        public ResponseEntity<UsersDTO> setUserDeals(
            @RequestParam String userId,
            @RequestParam String dealId,
            UriComponentsBuilder uriBuilder) {
        var location = uriBuilder.path("/users/deals/{userId}").buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).body(usersService.setDealsForUserId(userId, dealId));
    }

    @GetMapping("/users/deals/{userId}")
    public List<HomePageDealsDTO> getDealsByUserId(@PathVariable String userId) {
        return usersService.getDealsByUserId(userId);
    }

    @GetMapping("/users/coupons/{userId}")
    public List<CouponsCentralDTO> getCouponsByUserId(@PathVariable String userId){
        return usersService.getCouponsByUserId(userId);
    }

}
