package com.projects.ecommerce.Services;

import com.projects.ecommerce.DTO.mapper.EntityDTOMapper;
import com.projects.ecommerce.Entity.Category;
import com.projects.ecommerce.Entity.DTO.ShopByCategoryDTO;
import com.projects.ecommerce.Entity.ShopByCategory;
import com.projects.ecommerce.Repository.ShopByCategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ShopByCategoryService {
    private final ShopByCategoryRepo shopByCategoryRepo;
    private final CategoryService categoryService;
    private final EntityDTOMapper entityDTOMapper;

    public ShopByCategoryService(ShopByCategoryRepo shopByCategoryRepo, CategoryService categoryService, EntityDTOMapper entityDTOMapper) {
        this.shopByCategoryRepo = shopByCategoryRepo;
        this.categoryService = categoryService;
        this.entityDTOMapper = entityDTOMapper;
    }


    public Page<ShopByCategory> getAllCategoryDeals(Pageable p){
        return shopByCategoryRepo.findAll(p);
    }

    public Page<ShopByCategoryDTO> getAllCategoryDealsDTOPage(Pageable p){
        return entityDTOMapper.toShopByCategoryDTOPage(getAllCategoryDeals(p));
    }

    public ShopByCategory getCategoryDealsById(String id){
        return shopByCategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: "+id));
    }

    public ShopByCategoryDTO getCategoryDealsDTOById(String id){
        return entityDTOMapper.toShopByCategoryDTO(getCategoryDealsById(id));
    }

    public ShopByCategoryDTO saveCategoryDeals(ShopByCategoryDTO shopByCategory){
        if(shopByCategory.getCategory() == null || shopByCategory.getCategory().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category cannot be empty");
        }
        else if(shopByCategory.getHeading() == null || shopByCategory.getHeading().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Heading cannot be empty");
        }
        else if(shopByCategory.getImageUrl() == null || shopByCategory.getImageUrl().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image URL cannot be empty");
        }
        Category category = categoryService.getCategoryByCategoryName(shopByCategory.getCategory());
        ShopByCategory shopByCategory1 = new ShopByCategory();
        shopByCategory1.setCategory(category);
        shopByCategory1.setHeading(shopByCategory.getHeading());
        shopByCategory1.setImageUrl(shopByCategory.getImageUrl());
        return entityDTOMapper.toShopByCategoryDTO(shopByCategoryRepo.save(shopByCategory1));
    }

    public ShopByCategoryDTO upadteCategoryDealsById(ShopByCategoryDTO shopByCategory, String id){
        ShopByCategory existing = getCategoryDealsById(id);
        if(shopByCategory.getCategory() != null){
            Category category = categoryService.getCategoryByCategoryName(shopByCategory.getCategory());
            existing.setCategory(category);
        }
        if(shopByCategory.getHeading() != null) {
            existing.setHeading(shopByCategory.getHeading());
        }
        if(shopByCategory.getImageUrl() != null) {
            existing.setImageUrl(shopByCategory.getImageUrl());
        }
        return entityDTOMapper.toShopByCategoryDTO(shopByCategoryRepo.save(existing));
    }

    public void deleteCategoryDealsById(String id){
        if(!shopByCategoryRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Deals not found with id: "+ id);
        }
        shopByCategoryRepo.deleteById(id);
    }
}
