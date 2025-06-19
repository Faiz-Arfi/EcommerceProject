package com.projects.ecommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Entity.NavItems;
import com.projects.ecommerce.Services.NavItemsService;

@RestController
public class NavItemsController {
    @Autowired
    private NavItemsService navItemsService;

    @PostMapping("/navitems")
    public NavItems saveNavItems(@RequestBody NavItems navItems){
        return navItemsService.saveNavItems(navItems);
    }

    @GetMapping("/navitems")
    public List<NavItems> getAllItems(){
        return navItemsService.getAllItems();
    }

    @GetMapping("/navitems/{itemId}")
    public NavItems getItemById(@PathVariable String itemId){
        return navItemsService.getItemById(itemId);
    }

    @PutMapping("/navitems/{itemId}")
    public NavItems updateItemById(@RequestBody NavItems navItems, @PathVariable String itemId){
        return navItemsService.updateItemsById(navItems, itemId);
    }

    @DeleteMapping("/navitems/{itemId}")
    public void deleteItemById(@PathVariable String itemId){
        navItemsService.deleteItemById(itemId);
    }
}
