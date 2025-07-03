package com.projects.ecommerce.Controllers;

import com.projects.ecommerce.Entity.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projects.ecommerce.Entity.Product;
import com.projects.ecommerce.Services.ProductService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product")
    public Page<ProductDTO> getAllProducts(Pageable p){
        System.out.println(p.getSort());
        //validate if sorting parameters are valid
        productService.validateSortingParameters(p);
        return productService.getAllProductsDTO(p);
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable String id){
        try {
            return productService.getProductDTOById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id);
        }
    }

    @PostMapping("/admin/product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product, UriComponentsBuilder uriBuilder){
        ProductDTO savedProduct = productService.saveProduct(product);
        var location = uriBuilder.path("/product/{id}").buildAndExpand(savedProduct.getProductId()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/admin/product/{id}")
    public ProductDTO updateProductById(@RequestBody ProductDTO product, @PathVariable String id){
        return productService.updateProductById(product, id);
    }

    @DeleteMapping("/admin/product/{id}")
    public void deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
    }

    @GetMapping("/product/search")
    public Page<ProductDTO> searchProducts(@RequestParam String keyword, Pageable p){
        productService.validateSortingParameters(p);
        return productService.searchProductsDTO(keyword, p);
    }
}
