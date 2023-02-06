package com.frwk.marketplace.cart.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    
    List<CartItemEntity> findAllByCart(CartEntity cart);
}
