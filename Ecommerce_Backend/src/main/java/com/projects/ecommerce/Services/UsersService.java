package com.projects.ecommerce.Services;

import java.util.ArrayList;
import java.util.List;

import com.projects.ecommerce.Constants.TokenConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UsersRepo usersRepo;
    private final CouponsCentralService couponsCentralService;
    private final HomePageDealsService homePageDealsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityDTOMapper entityDTOMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UsersService(UsersRepo usersRepo, CouponsCentralService couponsCentralService, HomePageDealsService homePageDealsService, BCryptPasswordEncoder bCryptPasswordEncoder, EntityDTOMapper entityDTOMapper, AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.usersRepo = usersRepo;
        this.couponsCentralService = couponsCentralService;
        this.homePageDealsService = homePageDealsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entityDTOMapper = entityDTOMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public UsersDTO saveUser(Users user, boolean admin) {
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
        if(admin){
            user.setRoles(List.of("USER", "ADMIN"));
        }
        else{
            user.setRoles(List.of("USER"));
        }
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

    public String verifyUser(Users user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()
                )
        );

        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(user, 1000 * 60 * TokenConstants.accessTokenValidityMin);
        }
        return "failure";
    }

    public String generateRefreshToken(Users user) {
            return jwtService.generateToken(user, 1000 * 60 * TokenConstants.refreshTokenValidityMin);
    }

    public boolean verifyRefreshToken(String token){

        try{
            String username = jwtService.extractUserName(token);
            Users user = usersRepo.findByEmail(username);
            if(user == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with email: " + username);
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtService.isTokenValid(token, userDetails);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    public String newJwtToken(String token){
        String username = jwtService.extractUserName(token);
        Users user = usersRepo.findByEmail(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with email: " + username);
        }
        return jwtService.generateToken(user, 1000 * 60 * TokenConstants.accessTokenValidityMin);
    }

    public UsersDTO getUserDTOByUserName(@Email(message = "Email already registred") @NotNull(message = "Email cannot be null") String email) {
        Users user = usersRepo.findByEmail(email);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with email: " + email);
        }
        return entityDTOMapper.toUserDTO(user);
    }
}
