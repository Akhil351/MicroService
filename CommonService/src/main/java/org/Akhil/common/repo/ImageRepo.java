package org.Akhil.common.repo;

import org.Akhil.common.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image,String> {
    List<Image> findByProductId(String productId);
    void deleteAllByProductId(String productId);
}
