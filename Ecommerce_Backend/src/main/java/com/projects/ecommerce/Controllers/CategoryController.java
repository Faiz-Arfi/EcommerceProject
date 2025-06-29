package com.projects.ecommerce.Controllers;

import com.projects.ecommerce.Entity.Category;
import com.projects.ecommerce.Services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public Page<Category> getAllCategories(Pageable p){
        try{
            return categoryService.getAllCategories(p);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/category/{id}")
    public Category getCategoryByCategoryId(@PathVariable String id){
        try{
            return categoryService.getCategoryById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/admin/category")
    public Category saveCategory(@RequestBody Category category){
        try{
            return categoryService.saveCategory(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/admin/category/{id}")
    public Category updateCategoryById(@RequestBody Category newCategory, @PathVariable String id){
        try{
            return categoryService.updateCategoryById(newCategory, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/admin/category/{id}")
    public void deleteCategoryById(@PathVariable String id){
        try{
            categoryService.deleteCategoryById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
