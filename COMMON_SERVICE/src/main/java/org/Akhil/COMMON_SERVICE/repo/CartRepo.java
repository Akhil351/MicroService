package org.Akhil.COMMON_SERVICE.repo;

import org.Akhil.COMMON_SERVICE.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
}
