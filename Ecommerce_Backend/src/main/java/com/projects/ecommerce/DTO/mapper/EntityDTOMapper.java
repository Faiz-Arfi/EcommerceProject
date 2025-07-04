package com.projects.ecommerce.DTO.mapper;

import java.util.ArrayList;
import java.util.List;

import com.projects.ecommerce.Entity.*;
import com.projects.ecommerce.Entity.DTO.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
        dto.setHomePageDeals(toHomePageDealsDTOList(user.getHomePageDeals()));
        dto.setCouponsCentrals(toCouponsCentralDTOList(user.getCouponsCentrals()));
        return dto;
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
            dto.setCategoryId(product.getCategory().getCategoryId());
        } else {
            dto.setCategoryId(null);
        }

        return dto;
    }

    public Page<ProductDTO> toProductDTOPage(Page<Product> productList){
        return productList.map(this::toProductDTO);
    }

    public ShopByCategoryDTO toShopByCategoryDTO(ShopByCategory shopByCategory){
        return new ShopByCategoryDTO(
                shopByCategory.getId(),
                shopByCategory.getHeading(),
                shopByCategory.getCategory().getCategoryName(),
                shopByCategory.getImageUrl()
        );
    }

    public Page<ShopByCategoryDTO> toShopByCategoryDTOPage(Page<ShopByCategory> shopByCategoryList){
        return shopByCategoryList.map(this::toShopByCategoryDTO);
    }
}
