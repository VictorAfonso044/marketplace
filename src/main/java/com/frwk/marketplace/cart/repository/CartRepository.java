package com.frwk.marketplace.cart.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.ClientEntity;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    
    List<CartEntity> findAllByClient(ClientEntity client);
}
