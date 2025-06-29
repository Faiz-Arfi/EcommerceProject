package com.projects.ecommerce.Services;


import com.projects.ecommerce.Constants.SortConstants;
import com.projects.ecommerce.DTO.mapper.EntityDTOMapper;
import com.projects.ecommerce.Entity.Category;
import com.projects.ecommerce.Entity.DTO.ProductDTO;
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
    private final EntityDTOMapper entityDTOMapper;
    private final CategoryService categoryService;

    public ProductService(ProductRepo productRepo, UtilityService utilityService, EntityDTOMapper entityDTOMapper, CategoryService categoryService){
        this.productRepo = productRepo;
        this.utilityService = utilityService;
        this.entityDTOMapper = entityDTOMapper;
        this.categoryService = categoryService;
    }

    public Page<Product> getAllProducts(Pageable p) {
        return productRepo.findAll(p);
    }

    public Page<ProductDTO> getAllProductsDTO(Pageable p) {
        Page<Product> products = getAllProducts(p);
        return entityDTOMapper.toProductDTOPage(products);
    }

    public Product getProductById(String id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
    }

    public ProductDTO getProductDTOById(String id) {
        Product product = getProductById(id);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
        return entityDTOMapper.toProductDTO(product);
    }

    public ProductDTO saveProduct(Product product) {
        if (product.getCategory() == null || product.getCategory().getCategoryName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product category cannot be empty");
        }
        // Fetch the existing category from DB
        Category existingCategory = categoryService.getCategoryByCategoryName(product.getCategory().getCategoryName());
        product.setCategory(existingCategory);

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

        product.getCategory().getProducts().add(product);
        return entityDTOMapper.toProductDTO(productRepo.save(product));
    }

    public Page<Product> searchProducts(String keyword, Pageable p) {
        return productRepo.searchProduct(keyword, p);
    }

    public Page<ProductDTO> searchProductsDTO(String keyword, Pageable p) {
        Page<Product> products = searchProducts(keyword, p);
        return entityDTOMapper.toProductDTOPage(products);
    }

    public ProductDTO updateProductById(Product newProduct, String id) {
        Product oldProduct = getProductById(id);
        if(newProduct.getProductName() != null && !newProduct.getProductName().isEmpty()){
            oldProduct.setProductName(newProduct.getProductName());
        }
        if(newProduct.getBrand() != null && !newProduct.getBrand().isEmpty()){
            oldProduct.setBrand(newProduct.getBrand());
        }
        if(newProduct.getCategory() != null){
            if(oldProduct.getCategory() != null){
                oldProduct.getCategory().getProducts().remove(oldProduct);
            }
            oldProduct.setCategory(newProduct.getCategory());
            oldProduct.getCategory().getProducts().add(oldProduct);
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
        
        return entityDTOMapper.toProductDTO(productRepo.save(oldProduct));
    }

    public void deleteProductById(String id) {
        productRepo.deleteById(id);
    }

    public void validateSortingParameters(Pageable p) {
        Set<String> validSortFields = SortConstants.PRODUCT_SORT_FIELDS;
        utilityService.validateSearchParamater(p, validSortFields);
    }
}
