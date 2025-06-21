package com.projects.ecommerce.Services;


import com.projects.ecommerce.Constants.SortConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.ecommerce.Entity.Product;
import com.projects.ecommerce.Repository.ProductRepo;

import java.util.Set;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final UtilityService utilityService;

    public ProductService(ProductRepo productRepo, UtilityService utilityService){
        this.productRepo = productRepo;
        this.utilityService = utilityService;
    }

    public Page<Product> getAllProducts(Pageable p) {
        return productRepo.findAll(p);
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

    public Page<Product> searchProducts(String keyword, Pageable p) {
        return productRepo.searchProduct(keyword, p);
    }

    public Product updateProductById(Product newProduct, String id) {
        Product oldProduct = getProductById(id);
        if(newProduct.getProductName() != null && !newProduct.getProductName().isEmpty()){
            oldProduct.setProductName(newProduct.getProductName());
        }
        if(newProduct.getBrand() != null && !newProduct.getBrand().isEmpty()){
            oldProduct.setBrand(newProduct.getBrand());
        }
        if(newProduct.getProductCategory() != null && !newProduct.getProductCategory().isEmpty()){
            oldProduct.setProductCategory(newProduct.getProductCategory());
        }
        if(newProduct.getProductDescription() != null && !newProduct.getProductDescription().isEmpty()){
            oldProduct.setProductDescription(newProduct.getProductDescription());
        }
        if(newProduct.getProductImageUrl() != null && !newProduct.getProductImageUrl().isEmpty()){
            oldProduct.setProductImageUrl(newProduct.getProductImageUrl());
        }
        if(newProduct.getProductSize() != null && !newProduct.getProductSize().isEmpty()){
            oldProduct.setProductSize(newProduct.getProductSize());
        }

        if(newProduct.getProductPrice() > 0.0){
            oldProduct.setProductPrice(newProduct.getProductPrice());
        }
        
        return productRepo.save(oldProduct);
    }

    public void deleteProductById(String id) {
        productRepo.deleteById(id);
    }

    public void validateSortingParameters(Pageable p) {
        Set<String> validSortFields = SortConstants.PRODUCT_SORT_FIELDS;
        utilityService.validateSearchParamater(p, validSortFields);
    }
}
