package com.frwk.marketplace.cart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;
import com.frwk.marketplace.cart.service.CartService;
import com.frwk.marketplace.core.shared.constants.AppConstants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(AppConstants.API_CART_URL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartController implements CartControllerDoc {

    private final CartService cartService;

    @Override
    @PostMapping
    public ResponseEntity<CreatedCartDTO> createCart(@Valid @RequestBody ClientDTO body) {
        CreatedCartDTO cart = this.cartService.createCart(body);
        return ResponseEntity.status(cart.isNew() ? HttpStatus.CREATED : HttpStatus.OK).body(cart);
    }

    @Override
    @PostMapping("/produto")
    public ResponseEntity<Void> addProductInCart(@Valid @RequestBody AddedCartItemDTO body) {
        this.cartService.addProductInCart(body);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping(value="/fechar/{idCarrinho}")
    public ResponseEntity<ClosedCartDTO> closeCart(@PathVariable String idCarrinho) {
        ClosedCartDTO closedCart = this.cartService.closeCart(idCarrinho);
        return ResponseEntity.ok().body(closedCart);
    }
}
