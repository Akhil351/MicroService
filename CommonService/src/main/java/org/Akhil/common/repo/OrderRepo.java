package org.Akhil.common.repo;

import org.Akhil.common.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order,String> {
    List<Order> findByUserId(String userId);
}
