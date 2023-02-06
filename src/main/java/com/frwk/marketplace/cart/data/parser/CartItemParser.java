package com.frwk.marketplace.cart.data.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frwk.marketplace.cart.data.dto.ClosedCartItemDTO;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;
import com.frwk.marketplace.core.shared.DTOParser;
import com.frwk.marketplace.product.data.parser.ProductParser;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartItemParser implements DTOParser<ClosedCartItemDTO, CartItemEntity> {

    private ProductParser productParser;

    @Override
    public ClosedCartItemDTO mapToDTO(CartItemEntity entity) {
        return ClosedCartItemDTO.builder().produto(this.productParser.mapToDTO(entity.getProduct()))
                .quantidade(entity.getQuantity()).build();

    }

    @Override
    public CartItemEntity mapFromDTO(ClosedCartItemDTO dto) {
        return CartItemEntity.builder().product(this.productParser.mapFromDTO(dto.getProduto()))
                .quantity(dto.getQuantidade()).build();
    }

}
