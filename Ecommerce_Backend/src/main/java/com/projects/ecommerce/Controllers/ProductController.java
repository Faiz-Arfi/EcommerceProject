package com.projects.ecommerce.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.ecommerce.Entity.Product;
import com.projects.ecommerce.Services.ProductService;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getAllProducts(Pageable p){
        System.out.println(p.getSort());
        //validate if sorting parameters are valid
        productService.validateSortingParameters(p);
        return productService.getAllProducts(p);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, UriComponentsBuilder uriBuilder){
        Product savedProduct = productService.saveProduct(product);
        var location = uriBuilder.path("/product/{id}").buildAndExpand(savedProduct.getProductId()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    public Product updateProductById(@RequestBody Product product, @PathVariable String id){
        return productService.updateProductById(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(@RequestParam String keyword, Pageable p){
        productService.validateSortingParameters(p);
        return productService.searchProducts(keyword, p);
    }
}
