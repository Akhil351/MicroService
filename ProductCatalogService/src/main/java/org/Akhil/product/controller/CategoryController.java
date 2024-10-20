package org.Akhil.product.controller;


import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.model.Category;
import org.Akhil.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;



@RestController
@RequestMapping("/api/v2/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
            List<Category> categories=categoryService.getAllCategories();
            return ResponseEntity.ok(ApiResponse.builder().message("Found!").data(categories).build());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
            Category theCategory=categoryService.addCategory(category);
            return ResponseEntity.ok(ApiResponse.builder().message("Category Added").data(theCategory).build());
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
            Category theCategory=categoryService.getCategoryById(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Found!").data(theCategory).build());
    }

    @GetMapping("/getCategoryByName/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
            Category theCategory=categoryService.getCategoryByName(name);
            return ResponseEntity.ok(ApiResponse.builder().message("Found!").data(theCategory).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(ApiResponse.builder().message("Deleted SuccessFully").data(null).build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
            Category theCategory=categoryService.updateCategory(category,id);
            return ResponseEntity.ok(ApiResponse.builder().message("Updated SuccessFully").data(theCategory).build());
    }

    @GetMapping("/categoryName/{id}")
    public ResponseEntity<String> categoryName(@PathVariable Long id){
            Category theCategory=categoryService.getCategoryById(id);
            return ResponseEntity.ok(theCategory.getName());
    }




}
