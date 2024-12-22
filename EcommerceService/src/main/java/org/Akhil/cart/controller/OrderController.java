package org.Akhil.cart.controller;

import org.Akhil.cart.service.OrderService;
import org.Akhil.common.dto.OrderDto;
import org.Akhil.common.model.UserRequestContext;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@RequestMapping("/api/v2/orders")
@CrossOrigin(JwtUtils.BASE_URL)
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRequestContext context;
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(){
        OrderDto order=orderService.placeOrder(context.getUserId());
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data(order).build());
    }


    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable String orderId){
        OrderDto order=orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data(order).build());
    }

    @GetMapping("/getOrders")
    public ResponseEntity<ApiResponse> getOrderByUserId(){
        List<OrderDto> orders=orderService.getUserOrders(context.getUserId());
        return ResponseEntity.ok(ApiResponse.builder().status("Success").data(orders).build());
    }
}
