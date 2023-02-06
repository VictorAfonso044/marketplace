package com.frwk.marketplace.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.frwk.marketplace.cart.service.ClientService;
import com.frwk.marketplace.cart.service.impl.CartServiceImpl;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.product.data.dto.ProductDTO;
import com.frwk.marketplace.product.data.parser.ProductParser;
import com.frwk.marketplace.product.service.ProductService;
import com.frwk.marketplace.util.Creators;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private CartItemService cartItemService;

    @Mock
    private ProductService productService;

    @Mock
    private ClientParser clientParser;

    @Mock
    private ProductParser productParser;

    @Mock
    private CartItemParser cartItemParser;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void whenValidClientInformedThenItCreateCart() {
        ClientDTO client = Creators.createValidClient();
        CartEntity expectedCart = Creators.createValidCartEntity();
        
        Mockito.doCallRealMethod().when(clientParser).mapFromDTO(ArgumentMatchers.any());
        Mockito.when(this.clientService.createClient(ArgumentMatchers.any())).thenReturn(client);
        Mockito.when(this.cartRepository.findAllByClient(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
        Mockito.when(this.cartRepository.save(ArgumentMatchers.any())).thenReturn(expectedCart);

        CreatedCartDTO cartFound = this.cartService.createCart(client);

        assertEquals(expectedCart.getId().toString(), cartFound.getIdCarrinho());
        assertTrue(cartFound.isNew());
    }

    @Test
    void whenValidClientInformedThenCurrentCartReturned() {
        ClientDTO client = Creators.createValidClient();
        CartEntity expectedCart = Creators.createValidCartEntity();
        
        Mockito.doCallRealMethod().when(clientParser).mapFromDTO(ArgumentMatchers.any());
        Mockito.when(this.clientService.createClient(ArgumentMatchers.any())).thenReturn(client);
        Mockito.when(this.cartRepository.findAllByClient(ArgumentMatchers.any())).thenReturn(Collections.singletonList(expectedCart));

        CreatedCartDTO cartFound = this.cartService.createCart(client);

        assertEquals(expectedCart.getId().toString(), cartFound.getIdCarrinho());
        assertFalse(cartFound.isNew());
    }

    @Test
    void whenValidProductAndCartInformedThenProductAddedIntoCart() {
        AddedCartItemDTO addedCartItem = Creators.createCartItem(1);
        CartEntity cart = Creators.createValidCartEntity();
        ProductDTO productDTO = Creators.createProduct();

        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.productService.findProductById(ArgumentMatchers.any())).thenReturn(Optional.of(productDTO));
        Mockito.when(this.cartItemService.findAllByCart(ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        this.cartService.addProductInCart(addedCartItem);
        verify(this.cartItemService, times(1)).createCartItem(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void whenInvalidCartInformedThenThrowException() {
        AddedCartItemDTO addedCartItem = Creators.createCartItem(1);
        
        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        assertThrows(InvalidCartException.class, () -> this.cartService.addProductInCart(addedCartItem));
    }

    @Test
    void whenInvalidProductInformedThenThrowException() {
        AddedCartItemDTO addedCartItem = Creators.createCartItem(1);
        CartEntity cart = Creators.createValidCartEntity();

        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.productService.findProductById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        assertThrows(InvalidProductException.class, () -> this.cartService.addProductInCart(addedCartItem));
    }

    @Test
    void whenValidProductWithZeroQuantityInformedThenThrowException() {
        AddedCartItemDTO addedCartItem = Creators.createCartItem(0);
        CartEntity cart = Creators.createValidCartEntity();
        ProductDTO productDTO = Creators.createProduct();

        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.productService.findProductById(ArgumentMatchers.any())).thenReturn(Optional.of(productDTO));

        assertThrows(InvalidProductException.class, () -> this.cartService.addProductInCart(addedCartItem));
    }

    @Test
    void whenValidProductExistsInCartInformedThenIncrementProductQuantity() {
        AddedCartItemDTO addedCartItem = Creators.createCartItem(1);
        CartEntity cart = Creators.createValidCartEntity();
        ProductDTO productDTO = Creators.createProduct();
        CartItemEntity cartItem = Creators.createCartItemEntity(1);

        // Igualando os ids
        productDTO.setId(UUID.fromString(addedCartItem.getIdProduto()));
        cartItem.getProduct().setId(productDTO.getId());

        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.productService.findProductById(ArgumentMatchers.any())).thenReturn(Optional.of(productDTO));
        Mockito.when(this.cartItemService.findAllByCart(ArgumentMatchers.any())).thenReturn(Collections.singletonList(cartItem));

        this.cartService.addProductInCart(addedCartItem);

        verify(this.cartItemService, times(1)).saveCartItem(ArgumentMatchers.any());
    }

    @Test
    void whenValidCartIdInformedCartClosed() {
        CartEntity cart = Creators.createValidCartEntity();
        CartItemEntity cartItem = Creators.createCartItemEntity(1);

        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.cartItemService.findAllByCart(ArgumentMatchers.any())).thenReturn(Collections.singletonList(cartItem));
        Mockito.when(this.cartRepository.save(ArgumentMatchers.any())).thenReturn(cart);
        Mockito.doCallRealMethod().when(clientParser).mapToDTO(ArgumentMatchers.any());

        ClosedCartDTO closedCart = this.cartService.closeCart(cart.getId().toString());

        assertThat(cart.getId().toString(), is(equalTo(closedCart.getIdCarrinho())));
        assertThat(cart.getClient().getIdentification(), is(equalTo(closedCart.getCliente().getCpf())));
    }

    @Test
    void whenInvalidCartIdInformedExceptionThrow() {
        
        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        assertThrows(InvalidCartException.class, () -> this.cartService.closeCart(UUID.randomUUID().toString()));
    }

    @Test
    void whenValidCartWithNoItensInformedExceptionThrow() {
        CartEntity cart = Creators.createValidCartEntity();
        
        Mockito.when(this.cartRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(cart));
        Mockito.when(this.cartItemService.findAllByCart(ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        assertThrows(InvalidCartException.class, () -> this.cartService.closeCart(UUID.randomUUID().toString()));
    }
}
