package org.Akhil.CATEGORY_SERVICE.service.impl;

import org.Akhil.CATEGORY_SERVICE.service.CategoryService;
import org.Akhil.COMMON_SERVICE.exception.AlreadyExistException;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.COMMON_SERVICE.model.Category;
import org.Akhil.COMMON_SERVICE.repo.CategoryRepo;
import org.Akhil.COMMON_SERVICE.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name).orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        categoryRepo.findByName(category.getName())
                .ifPresentOrElse(categoryRepo::save,()->{throw new AlreadyExistException("This Category Already Present");
                });
        return category;
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return categoryRepo.findById(id)
                .map(existingCategory->this.updateExistingCategory(existingCategory,category))
                .map(categoryRepo::save)
                .orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
    }

    private Category updateExistingCategory(Category existingCategory,Category category){
        if(!ObjectUtils.isEmpty(category.getName())) existingCategory.setName(category.getName());
        return existingCategory;
    }
    @Override
    public void deleteCategoryById(Long id) {
         categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{throw new ResourceNotFoundException("Category Not Found");});
         productRepo.deleteAllByCategoryId(id);
    }
}
