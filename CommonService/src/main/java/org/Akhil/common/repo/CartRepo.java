package org.Akhil.common.repo;

import org.Akhil.common.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,String> {
    Optional<Cart> findByUserId(String userId);
}
