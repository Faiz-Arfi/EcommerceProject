package com.projects.ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Entity.NavItems;
import com.projects.ecommerce.Repository.NavItemsRepo;

@Service
public class NavItemsService {
    @Autowired
    private NavItemsRepo navItemsRepo;

    public NavItems saveNavItems(NavItems navItems) {
        if(navItemsRepo.existsByHeading(navItems.getHeading())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item with same heading already exists");
        }
        if(navItems.getHeading() == null || navItems.getHeading().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Heading can't be null or empty");
        }
        return navItemsRepo.save(navItems);
    }

    public List<NavItems> getAllItems() {
        return navItemsRepo.findAll();
    }

    public NavItems getItemById(String itemId) {
        return navItemsRepo.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found with id: "+ itemId));
    }

    public NavItems updateItemsById(NavItems navItems, String itemId) {
        NavItems currItems = getItemById(itemId);
        if(navItems.getHeading() != null){
            currItems.setHeading(navItems.getHeading());
        }
        if(navItems.getItems() != null){
            currItems.setItems(navItems.getItems());
        }
        return navItemsRepo.save(currItems);
    }

    public void deleteItemById(String itemId) {
        navItemsRepo.deleteById(itemId);
    }

}
