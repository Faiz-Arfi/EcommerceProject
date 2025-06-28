package com.projects.ecommerce.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.ecommerce.Entity.NavItems;
import com.projects.ecommerce.Services.NavItemsService;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class NavItemsController {

    private final NavItemsService navItemsService;

    public NavItemsController(NavItemsService navItemsService) {
        this.navItemsService = navItemsService;
    }

    @PostMapping("/admin/navitems")
    public ResponseEntity<NavItems> saveNavItems(@RequestBody NavItems navItems, UriComponentsBuilder uriBuilder) {
        NavItems savedNavItems = navItemsService.saveNavItems(navItems);
        var location = uriBuilder.path("/navitems/{itemId}").buildAndExpand(savedNavItems.getItemId()).toUri();
        return ResponseEntity.created(location).body(savedNavItems);
    }

    @GetMapping("/navitems")
    public List<NavItems> getAllItems(){
        return navItemsService.getAllItems();
    }

    @GetMapping("/navitems/{itemId}")
    public NavItems getItemById(@PathVariable String itemId){
        return navItemsService.getItemById(itemId);
    }

    @PutMapping("/admin/navitems/{itemId}")
    public NavItems updateItemById(@RequestBody NavItems navItems, @PathVariable String itemId){
        return navItemsService.updateItemsById(navItems, itemId);
    }

    @DeleteMapping("/admin/navitems/{itemId}")
    public void deleteItemById(@PathVariable String itemId){
        navItemsService.deleteItemById(itemId);
    }
}
