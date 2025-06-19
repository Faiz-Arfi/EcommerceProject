package com.projects.ecommerce.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projects.ecommerce.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword,'%')) OR " +
           "LOWER(p.productCategory) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchProduct(String keyword, Pageable p);

}
