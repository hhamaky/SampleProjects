package com.commercetools.stockHandlingTask.endpoints;

import com.commercetools.stockHandlingTask.dto.ProductStockDTO;
import com.commercetools.stockHandlingTask.dto.StockDTO;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import com.commercetools.stockHandlingTask.service.impl.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
public class StockController {

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Autowired
    private StockRepository stockRepository;

    @PostMapping(path = "/updateStock")
    public ResponseEntity updateStock(@RequestBody StockDTO updateStockDTO) {

        StockDTO stock = stockServiceImpl.updateStock(updateStockDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/stock/{productId}")
                .buildAndExpand(stock.getProductId())
                .toUri();
        return ResponseEntity.created(uri).build();

    }

    @GetMapping(path = "/stock")
    public ProductStockDTO getCurrentStock(@RequestParam("productId")  String productId) {
        Date requestTimestamp = new Date();
        return stockServiceImpl.getCurrentStock(productId, requestTimestamp);
    }

}
