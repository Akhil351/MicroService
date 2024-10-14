package org.Akhil.cart.service;

import org.Akhil.common.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto  placeOrder(String userId);
    OrderDto getOrder(String orderId);
    List<OrderDto> getUserOrders(String userId);
}
