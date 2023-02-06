package com.frwk.marketplace.product.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.data.entity.ProductEntity;
import com.frwk.marketplace.product.data.parser.ProductParser;
import com.frwk.marketplace.product.repository.ProductRepository;
import com.frwk.marketplace.product.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductParser parser;

    @Override
    public List<ProductDTO> listAllProducts() {
        List<ProductEntity> products = this.repository.findAll();
        return products.stream().map(this.parser::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findProductById(UUID id) {
        Optional<ProductEntity> product = this.repository.findById(id);
        if (product.isPresent()) {
            return Optional.of(this.parser.mapToDTO(product.get()));
        }
        return Optional.empty();
    }

    @Override
    public ProductDTO createOrUpdateProduct(ProductDTO product) {
        if(!verifyIfExist(product)){
            product.setId(UUID.randomUUID());
        }
        ProductEntity savedProduct = this.repository.save(this.parser.mapFromDTO(product));
        return this.parser.mapToDTO(savedProduct);
    }

    @Override
    public void deleteProduct(ProductDTO product) {
        this.repository.delete(this.parser.mapFromDTO(product));
    }

    private boolean verifyIfExist(ProductDTO product) {
        if (product == null || product.getId() == null) {
            return false;
        }
        return true;
    }
}
