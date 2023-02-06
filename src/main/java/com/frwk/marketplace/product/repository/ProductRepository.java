package com.frwk.marketplace.product.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.frwk.marketplace.product.data.entity.ProductEntity;

@Component
public interface ProductRepository extends JpaRepository<ProductEntity, UUID>{
    
}
