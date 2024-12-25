package org.Akhil.cart.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.cart.service.CartService;
import org.Akhil.cart.service.OrderService;
import org.Akhil.common.dto.OrderDto;
import org.Akhil.common.dto.OrderItemDto;
import org.Akhil.common.enums.OrderStatus;
import org.Akhil.common.exception.CartException;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.Cart;
import org.Akhil.common.model.Order;
import org.Akhil.common.model.OrderItem;
import org.Akhil.common.repo.CartItemRepo;
import org.Akhil.common.repo.OrderItemRepo;
import org.Akhil.common.repo.OrderRepo;
import org.Akhil.common.repo.ProductRepo;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private Converter converter;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private NewTopic topic;
    @Autowired
    private KafkaTemplate<String,OrderItem> template;

    @Transactional
    @Override
    public OrderDto placeOrder(String userId) {
        Cart cart=cartService.getCartByUserId(userId);
        if(cart.getTotalAmount().compareTo(BigDecimal.ZERO)==0){
             throw new CartException("The cart is empty. No items to order.");
        }
        Order order=this.createOrder(cart);
        List<OrderItem> orderItems=this.createOrderItems(order.getOrderId(), cart.getId());
        order.setTotalAmount(this.calculateTotalAmount(orderItems));
        Order saveOrder=orderRepo.save(order);
        orderItemRepo.saveAll(orderItems);
        cartService.clearCart(cart.getId());
        return this.convertToDto(saveOrder);
    }
    private Order createOrder(Cart cart){
        return   Order.builder()
                .orderId("or"+UUID.randomUUID().toString())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.code("Delivered"))
                .userId(cart.getUserId())
                .build();
    }
    private List<OrderItem> createOrderItems(String orderId,String cartId){
        return  cartItemRepo.findByCartId(cartId).stream().map(item->{
            OrderItem orderItem= OrderItem.builder().id("oi"+UUID.randomUUID()).orderId(orderId)
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .totalPrice(item.getTotalPrice())
                    .build();
            template.send(topic.name(),orderItem);
            return orderItem;
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
        OrderDto orderDto= converter.convertToDto(order,OrderDto.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"); // US format (12-hour with AM/PM)
        orderDto.setDateOfOrder(order.getOrderDate().format(formatter));
        orderDto.setStatus(OrderStatus.status(order.getOrderStatus()));
        orderDto.setOrderItems(orderItemRepo.findByOrderId(orderDto.getOrderId()).stream().map(this::convertToOrderDto).toList());
        return orderDto;
    }
    private OrderItemDto convertToOrderDto(OrderItem orderItem){
        OrderItemDto orderItemDto=converter.convertToDto(orderItem,OrderItemDto.class);
        List<Object[]> obj=productRepo.getNameAndImage(orderItem.getProductId()).orElseThrow(()->new ResourceNotFoundException("product not found"));
        Object[] productDetails = obj.getFirst();
        orderItemDto.setProductName((String) productDetails[0]);
        orderItemDto.setProductImageUrl((String) productDetails[1]);
        return orderItemDto;
    }

}
