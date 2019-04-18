package com.commercetools.stockHandlingTask.exception;

public class StockEntryExistsException extends RuntimeException {
    public StockEntryExistsException(String message) {
        super(message);
    }
}
