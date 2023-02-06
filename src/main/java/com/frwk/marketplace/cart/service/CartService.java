package com.frwk.marketplace.cart.service;

import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;

public interface CartService {
    
    CreatedCartDTO createCart(ClientDTO client);

    void addProductInCart(AddedCartItemDTO addedCartItem);

    ClosedCartDTO closeCart(String idCarrinho);
}
