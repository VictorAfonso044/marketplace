package com.frwk.marketplace.cart.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;
import com.frwk.marketplace.cart.repository.CartItemRepository;
import com.frwk.marketplace.cart.service.CartItemService;
import com.frwk.marketplace.product.data.entity.ProductEntity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItemEntity createCartItem(CartEntity cart, ProductEntity product, Integer quantity) {
        return this.saveCartItem(
                CartItemEntity.builder().id(UUID.randomUUID()).cart(cart).product(product).quantity(quantity).build());
    }

    @Override
    public CartItemEntity saveCartItem(CartItemEntity cartItem) {
        return this.cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItemEntity> findAllByCart(CartEntity cart) {
        return this.cartItemRepository.findAllByCart(cart);
    }
}
