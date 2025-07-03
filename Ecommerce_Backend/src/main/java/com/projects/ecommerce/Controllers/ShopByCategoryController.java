package com.projects.ecommerce.Controllers;

import com.projects.ecommerce.Entity.DTO.ShopByCategoryDTO;
import com.projects.ecommerce.Services.ShopByCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class ShopByCategoryController {

    private final ShopByCategoryService shopByCategoryService;

    public ShopByCategoryController(ShopByCategoryService shopByCategoryService){
        this.shopByCategoryService = shopByCategoryService;
    }

    @GetMapping("/shopbycategory")
    public Page<ShopByCategoryDTO> getAllShopCat(Pageable p){
        return shopByCategoryService.getAllCategoryDealsDTOPage(p);
    }

    @GetMapping("/shopbycategory/{id}")
    public ShopByCategoryDTO getShopCatById(@PathVariable String id){
        return shopByCategoryService.getCategoryDealsDTOById(id);
    }

    @PostMapping("/admin/shopbycategory")
    public ShopByCategoryDTO saveShopCat(@RequestBody ShopByCategoryDTO shopByCategory){
        return shopByCategoryService.saveCategoryDeals(shopByCategory);
    }

    @PutMapping("/admin/shopbycategory/{id}")
    public ShopByCategoryDTO updateShopCat(@RequestBody ShopByCategoryDTO shopByCategoryDTO, @PathVariable String id){
        return shopByCategoryService.upadteCategoryDealsById(shopByCategoryDTO, id);
    }

    @DeleteMapping("/admin/shopbycategory/{id}")
    public void deleteShopCat(@PathVariable String id){
        try{
            shopByCategoryService.deleteCategoryDealsById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
