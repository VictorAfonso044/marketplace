package com.frwk.marketplace.core.exceptions;

public class OpenCartWaitingException extends BaseException {

    public OpenCartWaitingException(String cartId) {
        super("OpenCartWaitingException", cartId);
    }
}
