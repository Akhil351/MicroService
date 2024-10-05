package org.Akhil.cart.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.cart.service.CartService;
import org.Akhil.cart.service.OrderService;
import org.Akhil.cart.service.ProductClient;
import org.Akhil.common.dto.OrderDto;
import org.Akhil.common.dto.OrderItemDto;
import org.Akhil.common.enums.OrderStatus;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.Cart;
import org.Akhil.common.model.Order;
import org.Akhil.common.model.OrderItem;
import org.Akhil.common.model.Product;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.OrderItemRepo;
import org.Akhil.common.repo.OrderRepo;
import org.Akhil.common.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private ProductRepo productRepo;

    @Transactional
    @Override
    public Order placeOrder(String userId) {
        Cart cart=cartService.getCartByUserId(userId);
        Order order=this.createOrder(cart);
        List<OrderItem> orderItems=this.createOrderItems(order.getOrderId(), cart.getId());
        order.setTotalAmount(this.calculateTotalAmount(orderItems));
 //       order.setOrderStatus(OrderStatus.DELIVERED);
        Order saveOrder=orderRepo.save(order);
        orderItemRepo.saveAll(orderItems);
        cartService.clearCart(cart.getId());
        return saveOrder;
    }
    private Order createOrder(Cart cart){
        return   Order.builder()
                .orderId(UUID.randomUUID().toString())
                .orderDate(LocalDate.now())
                .orderStatus(OrderStatus.PENDING)
                .userId(cart.getUserId())
                .build();
    }
    private List<OrderItem> createOrderItems(String orderId,Long cartId){
        return  cartItemRepo.findByCartId(cartId).stream().map(item->{
            Product product=productClient.getProductById(item.getProductId());
            product.setInventory(product.getInventory()-item.getQuantity());
            productRepo.save(product);
            return OrderItem.builder().orderId(orderId)
                    .productId(product.getId())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .totalPrice(item.getTotalPrice())
                    .build();
        }).toList();
    }

    @Override
    public OrderDto getOrder(String orderId) {
        return orderRepo.findById(orderId).map(this::convertToDto).orElseThrow(()->new ResourceNotFoundException("Order Not Found"));
    }

    @Override
    public List<OrderDto> getUserOrders(String userId) {
        return orderRepo.findByUserId(userId).stream().map(this::convertToDto).toList();
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderDto convertToDto(Order order){
        OrderDto orderDto= modelMapper.map(order,OrderDto.class);
        orderDto.setStatus(order.getOrderStatus().getStatus());
        orderDto.setOrderItems(orderItemRepo.findByOrderId(orderDto.getOrderId()).stream().map(this::convertToOrderDto).toList());
        return orderDto;
    }
    private OrderItemDto convertToOrderDto(OrderItem orderItem){
        OrderItemDto orderItemDto=modelMapper.map(orderItem,OrderItemDto.class);
        Product product=productClient.getProductById(orderItem.getProductId());
        orderItemDto.setProductName(product.getName());
        return orderItemDto;
    }

}
