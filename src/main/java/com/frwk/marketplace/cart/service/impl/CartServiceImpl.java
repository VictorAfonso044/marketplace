package com.frwk.marketplace.cart.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;
import com.frwk.marketplace.cart.data.entity.CartEntity;
import com.frwk.marketplace.cart.data.entity.CartItemEntity;
import com.frwk.marketplace.cart.data.parser.CartItemParser;
import com.frwk.marketplace.cart.data.parser.ClientParser;
import com.frwk.marketplace.cart.repository.CartRepository;
import com.frwk.marketplace.cart.service.CartItemService;
import com.frwk.marketplace.cart.service.CartService;
import com.frwk.marketplace.cart.service.ClientService;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidClientException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.core.shared.constants.AppConstants;
import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.data.parser.ProductParser;
import com.frwk.marketplace.product.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartServiceImpl implements CartService {
    private static final Logger LOGGER =  LoggerFactory.getLogger(CartService.class);

    private final ClientService clientService;
    private final CartItemService cartItemService;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ClientParser clientParser;
    private final ProductParser productParser;
    private final CartItemParser cartItemParser;

    @Override
    public CreatedCartDTO createCart(ClientDTO client) {
        ClientDTO retrivedClient = this.clientService.createClient(this.clientParser.mapFromDTO(client));
        CartEntity existCart = this.existsCartOpenForClient(retrivedClient);
        if (existCart != null) {
            return CreatedCartDTO.builder().idCarrinho(existCart.getId().toString()).isNew(false).build();
        }
        if (retrivedClient != null) {
            CartEntity createdCart = this.cartRepository.save(CartEntity.builder().id(UUID.randomUUID())
                    .client(this.clientParser.mapFromDTO(retrivedClient)).build());
            return CreatedCartDTO.builder().idCarrinho(createdCart.getId().toString()).isNew(true).build();
        }
        throw new InvalidClientException(AppConstants.INVALID_CLIENT_ERROR_MESSAGE);
    }

    @Override
    public void addProductInCart(AddedCartItemDTO addedCartItem) {
        CartEntity cart = this.validateCart(addedCartItem.getIdCarrinho());
        ProductDTO product = this.validateProduct(addedCartItem.getIdProduto());
        
        if (addedCartItem.getQuantidade() == null || addedCartItem.getQuantidade() < 1) {
            throw new InvalidProductException(AppConstants.INVALID_PRODUCT_QUANTITY_ERROR_MESSAGE);
        }
        List<CartItemEntity> currentItens = this.cartItemService.findAllByCart(cart);
        CartItemEntity existProductInCart = currentItens.stream()
                .filter(item -> product.getId().equals(item.getProduct().getId())).findFirst().orElse(null);
        if (existProductInCart != null) {
            existProductInCart.setQuantity(existProductInCart.getQuantity() + addedCartItem.getQuantidade());
            this.cartItemService.saveCartItem(existProductInCart);
            return;
        }
        
        this.cartItemService.createCartItem(cart, this.productParser.mapFromDTO(product), addedCartItem.getQuantidade());
    }

    @Override
    public ClosedCartDTO closeCart(String idCarrinho) {
        CartEntity cart = this.validateCart(idCarrinho);
        List<CartItemEntity> cartItens = this.cartItemService.findAllByCart(cart);
        if (cartItens == null || cartItens.isEmpty()) {
            throw new InvalidCartException(AppConstants.INVALID_CART_EMPTY_ERROR_MESSAGE);
        }
        this.setCartClosed(cart);
        return ClosedCartDTO.builder().idCarrinho(cart.getId().toString())
                .itens(cartItens.stream().map(item -> this.cartItemParser.mapToDTO(item)).collect(Collectors.toList()))
                .cliente(this.clientParser.mapToDTO(cart.getClient())).build();
    }

    private CartEntity existsCartOpenForClient(ClientDTO client) {
        List<CartEntity> cartsByClient = this.cartRepository.findAllByClient(this.clientParser.mapFromDTO(client));
        if (cartsByClient != null && !cartsByClient.isEmpty()) {
            return cartsByClient.stream().filter(cart -> !cart.isClosed()).findFirst().orElse(null);
        }
        return null;
    }

    private void setCartClosed(CartEntity cart) {
        cart.setClosed(true);
        this.cartRepository.save(cart);
    }

    private CartEntity validateCart(String cartId) {
        try {
            Optional<CartEntity> optCart = this.cartRepository.findById(UUID.fromString(cartId));
            if (!optCart.isPresent()) {
                LOGGER.debug("Carrinho com id {} não encontrado", cartId);
                throw new InvalidCartException(AppConstants.INVALID_CART_ERROR_MESSAGE);
            }

            if (optCart.get().isClosed()) {
                LOGGER.debug("Carrinho com id {} se encontra fechado", cartId);
                throw new InvalidCartException(AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE);
            }
            return optCart.get();
        } catch (IllegalArgumentException ex) {
            LOGGER.debug("String {} não é um identificador valido", cartId);
            throw new InvalidCartException(AppConstants.INVALID_CART_ERROR_MESSAGE);
        }
    }

    private ProductDTO validateProduct(String productId) {
        try {
            Optional<ProductDTO> optProduct = this.productService.findProductById(UUID.fromString(productId));
            if (!optProduct.isPresent()) {
                LOGGER.debug("Produto com id {} não encontrado", productId);
                throw new InvalidProductException(AppConstants.INVALID_PRODUCT_ERROR_MESSAGE);
            }
            return optProduct.get();
        } catch (IllegalArgumentException ex) {
            LOGGER.debug("String {} não é um identificador valido", productId);
            throw new InvalidProductException(AppConstants.INVALID_PRODUCT_ERROR_MESSAGE);
        }
    }
}
