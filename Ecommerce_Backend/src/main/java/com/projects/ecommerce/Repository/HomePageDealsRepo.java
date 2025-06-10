package com.projects.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.HomePageDeals;


@Repository
public interface HomePageDealsRepo extends JpaRepository<HomePageDeals, Long>{
    HomePageDeals findByDealName(String dealName);
}
