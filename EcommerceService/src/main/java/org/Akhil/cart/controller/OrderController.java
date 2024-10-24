package org.Akhil.cart.controller;

import org.Akhil.cart.service.OrderService;
import org.Akhil.common.dto.OrderDto;
import org.Akhil.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v2/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam String userId){
        OrderDto order=orderService.placeOrder(userId);
        return ResponseEntity.ok(ApiResponse.builder().message("Order Success").data(order).build());
    }


    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable String orderId){
        OrderDto order=orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(order).build());
    }

    @GetMapping("/{userId}/userOrders")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable String userId){
        List<OrderDto> orders=orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(orders).build());
    }
}
