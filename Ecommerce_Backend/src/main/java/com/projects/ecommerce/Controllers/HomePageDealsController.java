package com.projects.ecommerce.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Services.HomePageDealsService;


@RestController
public class HomePageDealsController {

    private final HomePageDealsService homePageDealsService;

    public HomePageDealsController(HomePageDealsService homePageDealsService) {
        this.homePageDealsService = homePageDealsService;
    }

    @GetMapping("/deals")
    public Page<HomePageDealsDTO> getAllDeals(Pageable p){
        homePageDealsService.validateSearchParameter(p);
        return homePageDealsService.getAllDealsDTOPage(p);
    }

    @GetMapping("/deals/id/{dealId}")
    public HomePageDealsDTO getDealsByDealId(@PathVariable String dealId){
        return homePageDealsService.getDealDTOByDealId(dealId);
    }

    @PostMapping("/deals")
    @ResponseStatus(HttpStatus.CREATED)
    public HomePageDealsDTO putDeals(@RequestBody HomePageDeals homePageDeals){
        return homePageDealsService.saveDeals(homePageDeals);
    }

    @PutMapping("/deals/{dealId}")
    public HomePageDealsDTO updateDeals(@PathVariable String dealId, @RequestBody HomePageDeals newDeals){
        return homePageDealsService.updateDealsByDealId(dealId, newDeals);
    }

    @DeleteMapping("/deals/{dealId}")
    public void deleteDeals(@PathVariable String dealId){
        homePageDealsService.deleteDealsByDealId(dealId);
    }

}
