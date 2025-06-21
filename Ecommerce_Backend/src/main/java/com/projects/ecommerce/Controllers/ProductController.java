package com.projects.ecommerce.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.ecommerce.Entity.Product;
import com.projects.ecommerce.Services.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product")
    public Page<Product> getAllProducts(Pageable p){
        System.out.println(p.getSort());
        //validate if sorting parameters are valid
        productService.validateSortingParameters(p);
        return productService.getAllProducts(p);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PutMapping("/product/{id}")
    public Product updateProductById(@RequestBody Product product, @PathVariable String id){
        return productService.updateProductById(product, id);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
    }

    @GetMapping("/product/search")
    public Page<Product> searchProducts(@RequestParam String keyword, Pageable p){
        productService.validateSortingParameters(p);
        return productService.searchProducts(keyword, p);
    }
}
