package com.projects.ecommerce.DTO.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.projects.ecommerce.Entity.CouponsCentral;
import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.Users;
import com.projects.ecommerce.Entity.DTO.CouponsCentralDTO;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Entity.DTO.UsersDTO;

@Component
public class EntityDTOMapper {
    public UsersDTO toUserDTO(Users user){
        if(user == null){
            return null;
        }
        UsersDTO dto = new UsersDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(null);
        dto.setHomePageDeals(toHomePageDealsDTOList(user.getHomePageDeals()));
        // dto.setCouponsCentrals(user.getCouponsCentrals());
        return dto;
    }
    public List<UsersDTO> toUsersDTOList(List<Users> userList){
        if(userList == null || userList.isEmpty()){
            return new ArrayList<>();
        }
        return userList.stream()
            .map(this::toUserDTO)
            .toList();
    }

    public HomePageDealsDTO toHomePageDealsDTO(HomePageDeals homePageDeals){
        if(homePageDeals == null){
            return null;
        }
        return new HomePageDealsDTO(
                homePageDeals.getDealId(),
                homePageDeals.getDealName(),
                homePageDeals.getImageUrl(),
                homePageDeals.getHeading(),
                homePageDeals.getSubHeading(),
                homePageDeals.getCategory()
        );
    }
    public List<HomePageDealsDTO> toHomePageDealsDTOList(List<HomePageDeals> homePageDealsList){
        if(homePageDealsList == null || homePageDealsList.isEmpty()){
            return new ArrayList<>();
        }
        return homePageDealsList.stream()
            .map(this::toHomePageDealsDTO)
            .toList();
    }

    public CouponsCentralDTO toCouponsCentralDTO(CouponsCentral couponsCentral){
        if(couponsCentral == null){
            return null;
        }
        return new CouponsCentralDTO(
            couponsCentral.getCouponId(),
            couponsCentral.getCouponCode(),
            couponsCentral.getDescription(),
            couponsCentral.getDescription(),
            couponsCentral.getCategory()
        );
    }
    public List<CouponsCentralDTO> toCouponsCentralDTOList(List<CouponsCentral> couponsCentralsList){
        if(couponsCentralsList == null || couponsCentralsList.isEmpty()){
            return new ArrayList<>();
        }
        return couponsCentralsList.stream()
            .map(this::toCouponsCentralDTO)
            .toList();
    }
}
