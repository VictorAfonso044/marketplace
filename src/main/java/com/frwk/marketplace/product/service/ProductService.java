package com.frwk.marketplace.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.frwk.marketplace.product.data.dto.ProductDTO;

public interface ProductService {
    
    List<ProductDTO> listAllProducts();
    Optional<ProductDTO> findProductById(UUID id);
    ProductDTO createOrUpdateProduct(ProductDTO product);
    void deleteProduct(ProductDTO product);
}
