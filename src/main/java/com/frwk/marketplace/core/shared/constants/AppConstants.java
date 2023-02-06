package com.frwk.marketplace.core.shared.constants;

public class AppConstants {
    
    public final static String API_VERSION = "/v1";
    public final static String API_CART_URL = API_VERSION + "/carrinho";
    public final static String API_PRODUCT_URL = API_VERSION + "/produto";

    public final static String VALIDATION_ERROR = "ModelValidationException";
    public final static String VALIDATION_ERROR_MESSAGE = "Dados enviados inválidos";
    
    public final static String INVALID_CLIENT_ERROR_MESSAGE = "Cliente não foi informado";

    public final static String INVALID_CART_ERROR_MESSAGE = "Carrinho informado é inválido";
    public final static String INVALID_CART_CLOSED_ERROR_MESSAGE = "Carrinho informado ja se encontra fechado";
    
    public final static String INVALID_CART_EMPTY_ERROR_MESSAGE = "O carrinho não possui produtos.";

    public final static String INVALID_PRODUCT_ERROR_MESSAGE = "Produto informado é inválido";
    public final static String INVALID_PRODUCT_QUANTITY_ERROR_MESSAGE = "A quantidade informada é menor que 1";

}
