package com.frwk.marketplace.cart.data.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.frwk.marketplace.product.data.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CART_ITEM")
@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
public class CartItemEntity {

    @Id
    @Column(name = "ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private CartEntity cart;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, columnDefinition = "BINARY(16) NOT NULL")
    private ProductEntity product;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
}
