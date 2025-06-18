package com.projects.ecommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Services.HomePageDealsService;


@RestController
public class HomePageDealsController {
    @Autowired
    public HomePageDealsService homePageDealsService;

    @GetMapping("/deals")
    public List<HomePageDealsDTO> getAllDeals(){
        try {
            return homePageDealsService.getAllDeals();
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deals/id/{dealId}")
    public HomePageDealsDTO getDealsByDealId(@PathVariable String dealId){
        try {
            return homePageDealsService.getDealByDealId(dealId);
        } catch (ResponseStatusException ex) {
            throw ex;
        }
    }

    @PostMapping("/deals")
    @ResponseStatus(HttpStatus.CREATED)
    public HomePageDealsDTO putDeals(@RequestBody HomePageDeals homePageDeals){
        return homePageDealsService.saveDeals(homePageDeals);
    }

    @PutMapping("/deals/{dealId}")
    public HomePageDealsDTO updateDeals(@PathVariable String dealId, @RequestBody HomePageDeals newDeals){
        try {
            return homePageDealsService.updateDealsByDealId(dealId, newDeals);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping("/deals/{dealId}")
    public void deleteDeals(@PathVariable String dealId){
        try {
            homePageDealsService.deleteDealsByDealId(dealId);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
