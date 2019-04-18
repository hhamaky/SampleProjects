package com.commercetools.stockHandlingTask.exception;

public class OutdatedStockRequestException extends RuntimeException {
    public OutdatedStockRequestException(String message) {
        super(message);
    }
}
