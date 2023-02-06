package com.frwk.marketplace.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.frwk.marketplace.cart.controller.CartController;
import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;
import com.frwk.marketplace.cart.service.CartService;
import com.frwk.marketplace.core.exceptions.InvalidCartException;
import com.frwk.marketplace.core.exceptions.InvalidProductException;
import com.frwk.marketplace.core.exceptions.handler.AppExceptionHandler;
import com.frwk.marketplace.core.shared.constants.AppConstants;
import com.frwk.marketplace.util.Creators;
import com.frwk.marketplace.util.JsonConvertionUtils;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    private final static String API_URL_ADD_PRODUCT = "/v1/carrinho/produto";
    private final static String API_URL_CLOSE_CART = "/v1/carrinho/fechar";

    private MockMvc mockMvc;

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new AppExceptionHandler())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPostToCreateCartIsCalledWithValidClient() throws Exception {
        ClientDTO entryDTO = Creators.createValidClient();
        CreatedCartDTO createdCartDTO = Creators.createValidCreatedCartDTO(true);

        Mockito.when(this.cartService.createCart(ArgumentMatchers.any())).thenReturn(createdCartDTO);

        this.mockMvc.perform(post(AppConstants.API_CART_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(entryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCarrinho", is(createdCartDTO.getIdCarrinho())));
    }

    @Test
    void whenPostToCreateCartIsCalledWithValidClientExistentCartReturned() throws Exception {
        ClientDTO entryDTO = Creators.createValidClient();
        CreatedCartDTO createdCartDTO = Creators.createValidCreatedCartDTO(false);

        Mockito.when(this.cartService.createCart(ArgumentMatchers.any())).thenReturn(createdCartDTO);

        this.mockMvc.perform(post(AppConstants.API_CART_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(entryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrinho", is(createdCartDTO.getIdCarrinho())));
    }

    @Test
    void whenPostToCreateCartIsCalledWithNoClient() throws Exception {
        ClientDTO emptyClient = ClientDTO.builder().build();

        this.mockMvc.perform(post(AppConstants.API_CART_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(emptyClient)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is(AppConstants.VALIDATION_ERROR)))
                .andExpect(jsonPath("$.message", is(AppConstants.VALIDATION_ERROR_MESSAGE)))
                .andExpect(jsonPath("$.errors.length()", is(4)));
    }

    @Test
    void whenPostCalledToAddValidProductIntoValidOpenCart() throws Exception {
        AddedCartItemDTO itemAdded = Creators.createCartItem(1);

        Mockito.doNothing().when(this.cartService).addProductInCart(ArgumentMatchers.any());

        this.mockMvc.perform(post(API_URL_ADD_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(itemAdded)))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostCalledToAddValidProductIntoInvalidCart() throws Exception {
        AddedCartItemDTO itemAdded = Creators.createCartItem(1);

        Mockito.doThrow(new InvalidCartException(AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE)).when(this.cartService)
                .addProductInCart(ArgumentMatchers.any());

        this.mockMvc.perform(post(API_URL_ADD_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(itemAdded)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is("InvalidCartException")))
                .andExpect(jsonPath("$.message", is(AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE)));
    }

    @Test
    void whenPostCalledToAddInvalidProductIntoValidCart() throws Exception {
        AddedCartItemDTO itemAdded = Creators.createCartItem(1);

        Mockito.doThrow(new InvalidProductException(AppConstants.INVALID_PRODUCT_ERROR_MESSAGE)).when(this.cartService)
                .addProductInCart(ArgumentMatchers.any());

        this.mockMvc.perform(post(API_URL_ADD_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(itemAdded)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is("InvalidProductException")))
                .andExpect(jsonPath("$.message", is(AppConstants.INVALID_PRODUCT_ERROR_MESSAGE)));
    }

    @Test
    void whenPostCalledToAddValidProductWithZeroQuantityIntoValidCart() throws Exception {
        AddedCartItemDTO itemAdded = Creators.createCartItem(0);

        this.mockMvc.perform(post(API_URL_ADD_PRODUCT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(itemAdded)))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is(AppConstants.VALIDATION_ERROR)))
                .andExpect(jsonPath("$.message", is(AppConstants.VALIDATION_ERROR_MESSAGE)))
                .andExpect(jsonPath("$.errors.[0].field", is("quantidade")))
                .andExpect(jsonPath("$.errors.[0].error", is("A quantidade informada deve ser maior que 0")));
    }

    @Test
    void whenPostCalledToCloseValidCart() throws Exception {
        ClosedCartDTO closedCart = Creators.createClosedCart();

        Mockito.when(this.cartService.closeCart((ArgumentMatchers.any()))).thenReturn(closedCart);

        this.mockMvc.perform(post(API_URL_CLOSE_CART + "/" + closedCart.getIdCarrinho()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrinho", is(closedCart.getIdCarrinho())))
                .andExpect(jsonPath("$.cliente.cpf", is(closedCart.getCliente().getCpf())))
                .andExpect(jsonPath("$.itens.[0].quantidade", is(2)))
                .andExpect(jsonPath("$.itens.[0].produto.nome", is("IPHONE 13 PRO MAX")));
    }

    @Test
    void whenPostCalledToCloseValidClosedCart() throws Exception {

        Mockito.when(this.cartService.closeCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException(AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE));

        this.mockMvc.perform(post(API_URL_CLOSE_CART + "/" + UUID.randomUUID()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is("InvalidCartException")))
                .andExpect(jsonPath("$.message", is(AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE)));
    }

    @Test
    void whenPostCalledToCloseInvalidCart() throws Exception {

        Mockito.when(this.cartService.closeCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException(AppConstants.INVALID_CART_ERROR_MESSAGE));

        this.mockMvc.perform(post(API_URL_CLOSE_CART + "/" + UUID.randomUUID()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is("InvalidCartException")))
                .andExpect(jsonPath("$.message", is(AppConstants.INVALID_CART_ERROR_MESSAGE)));
    }

    @Test
    void whenPostCalledToCloseValidEmptyCart() throws Exception {

        Mockito.when(this.cartService.closeCart((ArgumentMatchers.any())))
                .thenThrow(new InvalidCartException(AppConstants.INVALID_CART_EMPTY_ERROR_MESSAGE));

        this.mockMvc.perform(post(API_URL_CLOSE_CART + "/" + UUID.randomUUID()))
                .andExpect(status().isPreconditionFailed())
                .andExpect(jsonPath("$.type", is("InvalidCartException")))
                .andExpect(jsonPath("$.message", is(AppConstants.INVALID_CART_EMPTY_ERROR_MESSAGE)));
    }
}
