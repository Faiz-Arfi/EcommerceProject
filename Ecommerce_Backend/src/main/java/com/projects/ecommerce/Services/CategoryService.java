package com.projects.ecommerce.Services;

import com.projects.ecommerce.Entity.Category;
import com.projects.ecommerce.Repository.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Page<Category> getAllCategories(Pageable p){
        return categoryRepo.findAll(p);
    }

    public Category getCategoryById(String id){
        return categoryRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: "+id));
    }

    public Category saveCategory(Category category){
        if(category.getCategoryName() == null || category.getCategoryName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
        }

        if(categoryRepo.existsByCategoryName(category.getCategoryName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with same name already exists");
        }

        if(category.getProducts() != null){
            category.getProducts().forEach(product -> product.setCategory(category));
        }

        return categoryRepo.save(category);
    }

    public Category updateCategoryById(Category newCategory, String id){
        Category oldCategory = getCategoryById(id);

        if(newCategory.getCategoryName() != null && !newCategory.getCategoryName().isEmpty()){
            if(categoryRepo.existsByCategoryName(newCategory.getCategoryName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with same name already exists");
            }
            oldCategory.setCategoryName(newCategory.getCategoryName());
        }
        if(oldCategory.getProducts() != null){
            oldCategory.getProducts().forEach(product -> product.setCategory(newCategory));
        }
        return categoryRepo.save(oldCategory);
    }

    public void deleteCategoryById(String id){
        if(!categoryRepo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: "+id);
        }
        categoryRepo.deleteById(id);
    }

    public Category getCategoryByCategoryName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with name "+ categoryName + " does not exist"));
    }
}
