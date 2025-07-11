package com.projects.ecommerce.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Services.HomePageDealsService;
import org.springframework.web.util.UriComponentsBuilder;


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

    @GetMapping("/deals/{dealId}")
    public HomePageDealsDTO getDealsByDealId(@PathVariable String dealId){
        return homePageDealsService.getDealDTOByDealId(dealId);
    }

    @PostMapping("/admin/deals")
    public ResponseEntity<HomePageDealsDTO> putDeals(@RequestBody HomePageDeals homePageDeals, UriComponentsBuilder uriBuilder){
        HomePageDealsDTO savedHomePageDeal = homePageDealsService.saveDeals(homePageDeals);
        var location = uriBuilder.path("/deals/{dealId}").buildAndExpand(savedHomePageDeal.getDealId()).toUri();
        return ResponseEntity.created(location).body(savedHomePageDeal);
    }

    @PutMapping("/admin/deals/{dealId}")
    public HomePageDealsDTO updateDeals(@PathVariable String dealId, @RequestBody HomePageDeals newDeals){
        return homePageDealsService.updateDealsByDealId(dealId, newDeals);
    }

    @DeleteMapping("/admin/deals/{dealId}")
    public void deleteDeals(@PathVariable String dealId){
        homePageDealsService.deleteDealsByDealId(dealId);
    }

}
