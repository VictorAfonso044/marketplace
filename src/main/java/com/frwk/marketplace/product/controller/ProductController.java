package com.frwk.marketplace.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.core.shared.constants.AppConstants;
import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(AppConstants.API_PRODUCT_URL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController implements ProductControllerDoc {
    
    private final ProductService productService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> listProducts() {
        return this.productService.listAllProducts();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable String id) throws Exception {
        Optional<ProductDTO> product = this.productService.findProductById(UUID.fromString(id));
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createOrUpdateProduct(@Valid @RequestBody ProductDTO productDTO) {
        return this.productService.createOrUpdateProduct(productDTO);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable ProductDTO product) {
        this.productService.deleteProduct(product);
    }
}
