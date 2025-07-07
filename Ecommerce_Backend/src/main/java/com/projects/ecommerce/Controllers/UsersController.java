package com.projects.ecommerce.Controllers;

import java.util.List;
import java.util.Map;

import com.projects.ecommerce.Entity.DTO.LoginResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody Users user, UriComponentsBuilder uriBuilder){
        try{
            UsersDTO savedUser = usersService.saveUser(user, false);
            var location = uriBuilder.path("/users/{userId}").buildAndExpand(savedUser.getUserId()).toUri();
            return ResponseEntity.created(location).body(savedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @GetMapping("/admin/get-all-users")
    public Page<UsersDTO> getAllUsers(Pageable p){
        return usersService.getAllUsersDTO(p);
    }

    @GetMapping("/users")
    public UsersDTO getUserDetails(Authentication authentication){
        return usersService.getUserDTOByUserName(authentication.getName());
    }

    @GetMapping("/users/{userId}")
    public UsersDTO getUserById(@PathVariable String userId) {
        System.out.println("User ID: " + userId);
        return usersService.getUserDTOById(userId);
    }

    @PostMapping("/users/coupons")
    public ResponseEntity<UsersDTO> setUserCoupon(Authentication authentication,
            @RequestParam String couponId,
            UriComponentsBuilder uriBuilder) {
        String userId = usersService.getUserDTOByUserName(authentication.getName()).getUserId();
        var location = uriBuilder.path("/users/coupons}").buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).body(usersService.setCouponsForUserId(userId, couponId));
    }

    @PostMapping("/users/deals")
        public ResponseEntity<UsersDTO> setUserDeals(Authentication authentication,
            @RequestParam String dealId,
            UriComponentsBuilder uriBuilder) {
        String userId = usersService.getUserDTOByUserName(authentication.getName()).getUserId();
        var location = uriBuilder.path("/users/deals/{userId}").buildAndExpand(userId).toUri();
        return ResponseEntity.created(location).body(usersService.setDealsForUserId(userId, dealId));
    }

    @GetMapping("/users/deals")
    public Page<HomePageDealsDTO> getDealsByUserId(Authentication authentication, Pageable p) {
        return usersService.getDealsByUserName(authentication.getName(), p);
    }

    @GetMapping("/users/coupons")
    public Page<CouponsCentralDTO> getCouponsByUserId(Authentication authentication, Pageable p){
        return usersService.getCouponsByUserName(authentication.getName(), p);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody Users user) {
        String token = usersService.verifyUser(user);
        if ("failure".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        UsersDTO user1 = usersService.getUserDTOByUserName(user.getEmail());
        String refreshToken = usersService.generateRefreshToken(user);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .user(user1)
                .build();
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/moderator/add-admins")
    public ResponseEntity<UsersDTO> registerAdmin(@RequestBody Users user, UriComponentsBuilder uriBuilder){
        try{
            UsersDTO savedUser = usersService.saveUser(user, true);
            var location = uriBuilder.path("/users/{userId}").buildAndExpand(savedUser.getUserId()).toUri();
            return ResponseEntity.created(location).body(savedUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody Map<String, String> body){
        String refreshToken = body.get("refreshToken");
        String userId = body.get("userId");

        if(refreshToken != null && refreshToken.startsWith("Bearer ")){
            refreshToken = refreshToken.substring(7);
        }
        if(refreshToken == null || !usersService.verifyRefreshToken(refreshToken, userId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is either invalid or expired");
        }
        if(userId == null || userId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is either invalid or empty");
        }
        String newToken = usersService.newJwtToken(refreshToken);
        LoginResponseDTO response = LoginResponseDTO.builder()
                .accessToken(newToken)
                .refreshToken(refreshToken)
                .user(usersService.getUserDTOById(userId))
                .build();
        return ResponseEntity.ok(response);
    }

}
