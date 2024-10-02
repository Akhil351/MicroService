package org.Akhil.COMMON_SERVICE.repo;

import org.Akhil.COMMON_SERVICE.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItem> findByIdIn(Set<Long> cartIds);
}
