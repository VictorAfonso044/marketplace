package com.frwk.marketplace.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.frwk.marketplace.product.data.dto.ProductDTO;

import io.swagger.annotations.Api;

@Api("Produtos do marketplace")
public interface ProductControllerDoc {
    
    List<ProductDTO> listProducts();

    ResponseEntity<ProductDTO> findById(@PathVariable String id) throws Exception;

    ProductDTO createOrUpdateProduct(ProductDTO productDTO);

    void deleteProduct(@PathVariable ProductDTO product);
}
