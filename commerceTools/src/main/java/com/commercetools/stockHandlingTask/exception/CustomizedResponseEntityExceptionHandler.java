package com.commercetools.stockHandlingTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions
            (Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundExceptionException
            (ProductNotFoundException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OutdatedStockRequestException.class)
    public final ResponseEntity<Object> handleOutdatedStockRequestException
            (OutdatedStockRequestException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(StockEntryExistsException.class)
    public final ResponseEntity<Object> handleStockEntryExistsException
            (StockEntryExistsException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRangeException.class)
    public final ResponseEntity<Object> handleInvalidRangeException
            (InvalidRangeException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
