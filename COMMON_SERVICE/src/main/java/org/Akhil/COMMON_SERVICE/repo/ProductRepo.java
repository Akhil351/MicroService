package org.Akhil.COMMON_SERVICE.repo;

import org.Akhil.COMMON_SERVICE.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryIdAndBrand(Long categoryId,String brand);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brand,String name);
    Long countByBrandAndNameContaining(String brand,String name);
    void deleteAllByCategoryId(Long categoryId);
}
