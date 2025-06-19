package com.projects.ecommerce.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.DTO.mapper.EntityDTOMapper;
import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Entity.DTO.UsersDTO;
import com.projects.ecommerce.Repository.UsersRepo;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private CouponsCentralService couponsCentralService;
    @Autowired
    private HomePageDealsService homePageDealsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EntityDTOMapper entityDTOMapper;

    public UsersDTO saveUser(Users user) {
        if(usersRepo.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already exists with the same email");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
        }
        else if(user.getFirstName() == null || user.getFirstName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name cannot be null or empty");
        }
        else if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
        }
        else if(user.getPassword().length() < 8){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }
        else if(!user.getPassword().matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return entityDTOMapper.toUserDTO(usersRepo.save(user));
    }

    public Page<UsersDTO> getAllUsersDTO(Pageable p) {
        return entityDTOMapper.toUsersDTOPage(usersRepo.findAll(p));
    }
    
    public Users getUserById(String userId) {
        return usersRepo.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
    }

    public UsersDTO getUserDTOById(String userId) {
        Users user = getUserById(userId);
        return entityDTOMapper.toUserDTO(user);
    }

    public UsersDTO setCouponsForUserId(String userId, String couponId){
        Users user = getUserById(userId);
        
        if (user.getCouponsCentrals() == null) {
            user.setCouponsCentrals(new ArrayList<>());
        }
        
        if(user.getCouponsCentrals().stream().anyMatch(c -> c.getCouponId().equalsIgnoreCase(couponId))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon already exists for this user");
        }

        CouponsCentral coupon = couponsCentralService.getCouponByCouponCodeId(couponId);

        user.getCouponsCentrals().add(coupon);
        return entityDTOMapper.toUserDTO(usersRepo.save(user));  
    }

    public UsersDTO setDealsForUserId(String userId, String dealId){
        Users user = getUserById(userId);
        
        if (user.getHomePageDeals() == null) {
            user.setHomePageDeals(new ArrayList<>());
        }
        
        if(user.getHomePageDeals().stream().anyMatch(c -> c.getDealId().equalsIgnoreCase(dealId))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coupon already exists for this user");
        }

        HomePageDeals deal = homePageDealsService.getDealbyDealId(dealId);

        user.getHomePageDeals().add(deal);
        return entityDTOMapper.toUserDTO(usersRepo.save(user));  
    }

    public List<HomePageDealsDTO> getDealsByUserId(String userId) {
        Users user = getUserById(userId);
        return entityDTOMapper.toHomePageDealsDTOList(user.getHomePageDeals());
    }

    public List<CouponsCentralDTO> getCouponsByUserId(String userId){
        Users user = getUserById(userId);
        return entityDTOMapper.toCouponsCentralDTOList(user.getCouponsCentrals());
    }

}
