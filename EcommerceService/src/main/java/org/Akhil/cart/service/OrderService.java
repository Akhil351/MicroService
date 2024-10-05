package org.Akhil.cart.service;

import org.Akhil.common.dto.OrderDto;
import org.Akhil.common.model.Order;

import java.util.List;

public interface OrderService {
    Order  placeOrder(String userId);
    OrderDto getOrder(String orderId);
    List<OrderDto> getUserOrders(String userId);
}
