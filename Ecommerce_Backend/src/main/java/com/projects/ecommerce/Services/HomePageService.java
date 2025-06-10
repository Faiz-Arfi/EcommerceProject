package com.projects.ecommerce.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Repository.HomePageDealsRepo;


@Service
public class HomePageService {
    @Autowired
    public HomePageDealsRepo homePageDealsRepo;

    public HomePageDeals getDealsByDealName(String dealName) {
        return homePageDealsRepo.findByDealName(dealName);
    }

    public void saveDealsByDealName(HomePageDeals homePageDeals) {
        homePageDealsRepo.save(homePageDeals);        
    }

}
