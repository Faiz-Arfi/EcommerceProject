package com.projects.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.NavItems;

@Repository
public interface NavItemsRepo extends JpaRepository<NavItems, String>{

    boolean existsByHeading(String heading);

}
