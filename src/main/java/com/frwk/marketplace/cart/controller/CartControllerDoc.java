package com.frwk.marketplace.cart.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.frwk.marketplace.cart.data.dto.AddedCartItemDTO;
import com.frwk.marketplace.cart.data.dto.ClientDTO;
import com.frwk.marketplace.cart.data.dto.ClosedCartDTO;
import com.frwk.marketplace.cart.data.dto.CreatedCartDTO;
import com.frwk.marketplace.core.shared.constants.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("API de controle do carrinho do marketplace")
public interface CartControllerDoc {
    
    @ApiOperation(value = "Operação de criação de um carrinho")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o ID do carrinho criado."),
            @ApiResponse(code = 200, message = "Retorna o ID do carrinho que se encontra aberto."),
            @ApiResponse(code = 412, message = "Dados obrigatorios faltando ou incorretos."),
            @ApiResponse(code = 412, message = "Cliente não informado")
    })
    ResponseEntity<CreatedCartDTO> createCart(ClientDTO body);

    @ApiOperation(value = "Operação para adicionar um produto a um carrinho")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto adicionado com sucesso"),
            @ApiResponse(code = 412, message = "Dados obrigatorios faltando ou incorretos."),
            @ApiResponse(code = 412, message = AppConstants.INVALID_CART_ERROR_MESSAGE),
            @ApiResponse(code = 412, message = AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE),
            @ApiResponse(code = 412, message = AppConstants.INVALID_PRODUCT_QUANTITY_ERROR_MESSAGE),
            @ApiResponse(code = 412, message = AppConstants.INVALID_PRODUCT_ERROR_MESSAGE)
    })
    ResponseEntity<Void> addProductInCart(@Valid @RequestBody AddedCartItemDTO body);

    @ApiOperation(value = "Operação para adicionar um produto a um carrinho")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna dados do carinho fechado"),
            @ApiResponse(code = 412, message = AppConstants.INVALID_CART_ERROR_MESSAGE),
            @ApiResponse(code = 412, message = AppConstants.INVALID_CART_CLOSED_ERROR_MESSAGE),
            @ApiResponse(code = 412, message = AppConstants.INVALID_CART_EMPTY_ERROR_MESSAGE),
    })
    ResponseEntity<ClosedCartDTO> closeCart(@PathVariable String idCarrinho);
}
