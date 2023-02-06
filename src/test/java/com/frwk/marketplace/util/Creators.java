package com.frwk.marketplace.util;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;
import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;
import com.frwk.marketplace.cart.data.entity.ClientEntity;
import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.data.entity.ProductEntity;

public class Creators {

    public static ClientDTO createValidClient() {
        return ClientDTO.builder().nome("Victor").cpf("126.931.436-08").dataNascimento("15/06/2003")
                .email("victorafonso@gmail.com").build();
    }

    public static CartEntity createValidCartEntity() {
        return CartEntity.builder().id(UUID.randomUUID()).client(createClientEntity()).itens(Collections.emptyList()).isClosed(false).build();
    }

    public static CreatedCartDTO createValidCreatedCartDTO(boolean isNew) {
        return CreatedCartDTO.builder().idCarrinho(UUID.randomUUID().toString()).isNew(isNew).build();
    }

    public static AddedCartItemDTO createCartItem(Integer quantity) {
        return AddedCartItemDTO.builder().idCarrinho(UUID.randomUUID().toString())
                .idProduto(UUID.randomUUID().toString()).quantidade(quantity).build();
    }

    public static ProductDTO createProduct() {
        return ProductDTO.builder().id(UUID.randomUUID()).nome("IPHONE 13 PRO MAX").preco(1000.99).build();
    }

    public static ClosedCartItemDTO createClosedCartItem(Integer quantity) {
        return ClosedCartItemDTO.builder().produto(createProduct()).quantidade(quantity).build();
    }

    public static ClosedCartDTO createClosedCart() {
        return ClosedCartDTO.builder().idCarrinho(UUID.randomUUID().toString()).cliente(createValidClient())
                .itens(Collections.singletonList(createClosedCartItem(2)))
                .build();
    }

    public static ProductEntity createProductEntity() {
        return ProductEntity.builder().id(UUID.randomUUID()).name("IPHONE 13 PRO MAX").price(1000.99).build();
    }

    public static CartItemEntity createCartItemEntity(Integer quantity) {
        return CartItemEntity.builder().id(UUID.randomUUID()).cart(createValidCartEntity())
                .product(createProductEntity())
                .quantity(quantity).build();
    }

    
    public static ClientEntity createClientEntity() {
        return ClientEntity.builder().name("Victor").identification("126.931.436-08").birthday(new Date())
        .email("victorafonso@gmail.com").build();
    }

}
