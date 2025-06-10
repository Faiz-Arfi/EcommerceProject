package com.projects.ecommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Services.HomePageService;


@RestController
public class HomePageController {
    @Autowired
    public HomePageService homePageService;
    @GetMapping("/deals/{dealName}")
    public HomePageDeals getDeals(@PathVariable String dealName){
        System.out.println(dealName);
        return homePageService.getDealsByDealName(dealName);
    }
    @PostMapping("/deals")
    public void putDeals(@RequestBody HomePageDeals homePageDeals){
        homePageService.saveDealsByDealName(homePageDeals);
    }
}
