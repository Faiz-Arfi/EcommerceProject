package com.projects.ecommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Entity.Product;
import com.projects.ecommerce.Repository.ProductRepo;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
    }

    public Product saveProduct(Product product) {
        if(product.getProductCategory() == null || product.getProductCategory().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product category cannot be empty");
        }
        if(product.getProductName() == null || product.getProductName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be empty");
        }
        if(product.getProductImageUrl() == null || product.getProductImageUrl().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product image URL cannot be empty");
        }
        if(product.getProductDescription() == null || product.getProductDescription().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product description cannot be empty");
        }
        if(product.getBrand() == null || product.getBrand().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product brand cannot be empty");
        }
        if(product.getProductPrice() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price must be greater than zero");
        }
        System.out.println(product.getProductPrice());
        return productRepo.save(product);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProduct(keyword);
    }
}
