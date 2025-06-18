package com.projects.ecommerce.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.DTO.mapper.EntityDTOMapper;
import com.projects.ecommerce.Entity.HomePageDeals;
import com.projects.ecommerce.Entity.DTO.HomePageDealsDTO;
import com.projects.ecommerce.Repository.HomePageDealsRepo;


@Service
public class HomePageDealsService {
    @Autowired
    private HomePageDealsRepo homePageDealsRepo;
    @Autowired
    private EntityDTOMapper entityDTOMapper;

    public HomePageDealsDTO saveDeals(HomePageDeals homePageDeals) {
        if(homePageDealsRepo.existsByDealName(homePageDeals.getDealName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same deal name is already available");
        }
        try {
            return entityDTOMapper.toHomePageDealsDTO(homePageDealsRepo.save(homePageDeals));    
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save deal", ex);
        }
    }

    public List<HomePageDealsDTO> getAllDeals() {
        try {
            List<HomePageDeals> homePageDealsList = homePageDealsRepo.findAll();
            //convert to DTO and return
            return entityDTOMapper.toHomePageDealsDTOList(homePageDealsList);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to fetch all deals", ex);
        }
    }

    public HomePageDealsDTO getDealByDealId(String dealId) {
        HomePageDeals homePageDeals = homePageDealsRepo.findById(dealId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deal not found with id: " + dealId));
        return entityDTOMapper.toHomePageDealsDTO(homePageDeals);
    }

    public HomePageDealsDTO updateDealsByDealId(String dealId, HomePageDeals homePageDeals) {
        HomePageDeals existingDeal = homePageDealsRepo.findById(dealId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deal not found with id: " + dealId));
        if(homePageDealsRepo.existsByDealName(homePageDeals.getDealName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deal name already exists");
        }
        if(homePageDeals.getDealName() != null && !homePageDeals.getDealName().isEmpty()){
            existingDeal.setDealName(homePageDeals.getDealName());
        }
        if(homePageDeals.getImageUrl() != null && !homePageDeals.getImageUrl().isEmpty()){
            existingDeal.setImageUrl(homePageDeals.getImageUrl());
        }
        if(homePageDeals.getHeading() != null && !homePageDeals.getHeading().isEmpty()){
            existingDeal.setHeading(homePageDeals.getHeading());
        }
        if(homePageDeals.getSubHeading() != null && !homePageDeals.getSubHeading().isEmpty()){
            existingDeal.setSubHeading(homePageDeals.getSubHeading());
        }
        if(homePageDeals.getCategory() != null && !homePageDeals.getCategory().isEmpty()){
            existingDeal.setCategory(homePageDeals.getCategory());
        }
        
        try {
            return entityDTOMapper.toHomePageDealsDTO(homePageDealsRepo.save(existingDeal));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update deal", e);
        }
    }

    public void deleteDealsByDealId(String dealId) {
        if (!homePageDealsRepo.existsById(dealId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deal not found with id: " + dealId);
        }
        homePageDealsRepo.deleteById(dealId);
    }

}
