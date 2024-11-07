package org.Akhil.common.repo;

import org.Akhil.common.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,String> {
    List<OrderItem> findByOrderId(String orderId);
}
