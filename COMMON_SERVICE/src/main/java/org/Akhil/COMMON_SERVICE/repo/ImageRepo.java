package org.Akhil.COMMON_SERVICE.repo;

import org.Akhil.COMMON_SERVICE.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long productId);
    void deleteAllByProductId(Long productId );
}
