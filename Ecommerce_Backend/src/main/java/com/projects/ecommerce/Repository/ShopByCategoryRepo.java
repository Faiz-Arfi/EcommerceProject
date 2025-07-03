package com.projects.ecommerce.Repository;

import com.projects.ecommerce.Entity.ShopByCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopByCategoryRepo extends JpaRepository<ShopByCategory, String> {
//    boolean existsByCategory(String categoryName);
}
