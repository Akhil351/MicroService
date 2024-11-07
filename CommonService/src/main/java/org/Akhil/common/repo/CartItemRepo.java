package org.Akhil.common.repo;

import org.Akhil.common.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,String> {
    void deleteAllByCartId(String cartId);
    Optional<CartItem> findByCartIdAndProductId(String cartId, String productId);
    List<CartItem> findByIdIn(Set<String> cartIds);
    List<CartItem> findByCartId(String cartId);
}
