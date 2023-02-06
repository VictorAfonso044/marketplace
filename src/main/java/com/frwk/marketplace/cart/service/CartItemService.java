package com.frwk.marketplace.cart.service;

import java.util.List;

import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;
import com.frwk.marketplace.product.data.entity.ProductEntity;

public interface CartItemService {
    
    List<CartItemEntity> findAllByCart(CartEntity cart);
    CartItemEntity createCartItem(CartEntity cart, ProductEntity product, Integer quantity);
    CartItemEntity saveCartItem(CartItemEntity cartItem);
}
