package org.Akhil.CART_ITEM_SERVICE.service.impl;

import org.Akhil.CART_ITEM_SERVICE.service.CartClient;
import org.Akhil.CART_ITEM_SERVICE.service.CartItemService;
import org.Akhil.CART_ITEM_SERVICE.service.ProductClient;
import org.Akhil.COMMON_SERVICE.exception.ResourceNotFoundException;
import org.Akhil.COMMON_SERVICE.model.Cart;
import org.Akhil.COMMON_SERVICE.model.CartItem;
import org.Akhil.COMMON_SERVICE.model.Product;
import org.Akhil.COMMON_SERVICE.repo.CartItemRepo;
import org.Akhil.COMMON_SERVICE.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;


@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private CartClient cartClient;
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductClient productClient;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart=cartClient.getCart(cartId);
        Product product=productClient.getProductById(productId);
        CartItem cartItem=cartItemRepo.findByCartIdAndProductId(cartId,productId).orElse(new CartItem());
        if(ObjectUtils.isEmpty(cartItem.getId())){
            cartItem.setCartId(cartId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        cart.getCartItemIds().add(cartItem.getId());
        cart.setTotalAmount(this.updateTotalAmountInCart(cart));
        cartRepo.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
           Cart cart=cartClient.getCart(cartId);
           CartItem cartItemToRemove=this.getCartItem(cartId,productId);
           cartItemToRemove.setCartId(null);
           cartItemRepo.delete(cartItemToRemove);
           cart.getCartItemIds().remove(cartItemToRemove.getId());
           cart.setTotalAmount(this.updateTotalAmountInCart(cart));
           cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart=cartClient.getCart(cartId);
        CartItem cartItem=this.getCartItem(cartId,productId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        cart.setTotalAmount(this.updateTotalAmountInCart(cart));
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
       return cartItemRepo.findByCartIdAndProductId(cartId,productId).orElseThrow(()->new ResourceNotFoundException("Item Not Found"));
    }

    private BigDecimal updateTotalAmountInCart(Cart cart){
         return  cartItemRepo.findByIdIn(cart.getCartItemIds()).stream()
                .map(item->{
                    return item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
