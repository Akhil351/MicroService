package org.Akhil.common.repo;

import org.Akhil.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {
    List<Product> findByCategoryId(String categoryId);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryIdAndBrand(String categoryId,String brand);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brand,String name);
    Long countByBrandContainingAndNameContaining(String brand,String name);
    void deleteAllByCategoryId(String categoryId);
}
