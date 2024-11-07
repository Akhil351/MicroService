package org.Akhil.product.service;

import org.Akhil.common.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(String id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,String id);
    void deleteCategoryById(String id);
}
