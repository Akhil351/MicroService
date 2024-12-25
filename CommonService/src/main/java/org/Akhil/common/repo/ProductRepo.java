package org.Akhil.common.repo;

import org.Akhil.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {
    Long countByBrandContainingAndNameContaining(String brand,String name);
    void deleteAllByCategoryId(String categoryId);
    @Query(value = "SELECT p.price FROM Product p where p.id=:id",nativeQuery = false) // it works with entity and fields
    Optional<BigDecimal> getPrice(@Param("id") String productId);
    @Query(value = "SELECT p.name,p.image FROM Product p where p.id=:id",nativeQuery = false)
    Optional<List<Object[]>> getNameAndImage(@Param("id") String productId);
}
