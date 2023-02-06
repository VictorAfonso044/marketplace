package com.frwk.marketplace.product.data.parser;

import org.springframework.stereotype.Component;

import com.frwk.marketplace.core.shared.DTOParser;
import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.data.entity.ProductEntity;

@Component
public class ProductParser implements DTOParser<ProductDTO, ProductEntity> {

    @Override
    public ProductDTO mapToDTO(ProductEntity entity) {
        return ProductDTO.builder().id(entity.getId()).nome(entity.getName()).preco(entity.getPrice()).build();
    }

    @Override
    public ProductEntity mapFromDTO(ProductDTO dto) {
        return ProductEntity.builder().id(dto.getId()).name(dto.getNome()).price(dto.getPreco()).build();
    }
}
