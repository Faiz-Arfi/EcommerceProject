package com.projects.ecommerce.DTO.mapper;

import java.util.ArrayList;
import java.util.List;

import com.projects.ecommerce.Entity.*;
import com.projects.ecommerce.Entity.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
        dto.setPassword(user.getPassword());
        dto.setHomePageDeals(toHomePageDealsDTOList(user.getHomePageDeals()));
        dto.setCouponsCentrals(toCouponsCentralDTOList(user.getCouponsCentrals()));
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

    public Page<UsersDTO> toUsersDTOPage(Page<Users> userList){
        return userList.map(this::toUserDTO);
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

    public Page<HomePageDealsDTO> toHomePageDealsDTOPage(Page<HomePageDeals> homePageDealsList){
        return homePageDealsList.map(this::toHomePageDealsDTO);
    }

    public CouponsCentralDTO toCouponsCentralDTO(CouponsCentral couponsCentral){
        if(couponsCentral == null){
            return null;
        }
        return new CouponsCentralDTO(
            couponsCentral.getCouponId(),
            couponsCentral.getCouponCode(),
            couponsCentral.getHeading(),
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
    public Page<CouponsCentralDTO> toCouponsCentralDTOPage(Page<CouponsCentral> couponsCentralsList){
        return couponsCentralsList.map(this::toCouponsCentralDTO);
    }

    public ProductDTO toProductDTO(Product product){
        if(product == null){
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setBrand(product.getBrand());
        dto.setProductImageUrl(product.getProductImageUrl());
        if (product.getCategory() != null) {
            dto.setCategory(product.getCategory().getCategoryName());
        } else {
            dto.setCategory("Unknown");
        }

        return dto;
    }

    public List<ProductDTO> toProductDTOList(List<Product> productList){
        if(productList == null || productList.isEmpty()){
            return new ArrayList<>();
        }
        return productList.stream()
            .map(this::toProductDTO)
            .toList();
    }

    public Page<ProductDTO> toProductDTOPage(Page<Product> productList){
        return productList.map(this::toProductDTO);
    }
}
