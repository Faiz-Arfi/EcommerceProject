package com.projects.ecommerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.HomePageDeals;


@Repository
public interface HomePageDealsRepo extends JpaRepository<HomePageDeals, String>{
    Optional<HomePageDeals> findByDealName(String dealName);

    boolean existsByDealName(String dealName);
}
