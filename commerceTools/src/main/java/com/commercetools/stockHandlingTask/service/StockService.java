package com.commercetools.stockHandlingTask.service;

import com.commercetools.stockHandlingTask.dto.ProductStockDTO;
import com.commercetools.stockHandlingTask.dto.StockDTO;

import java.util.Date;

public interface StockService {

    public ProductStockDTO getCurrentStock(String productId, Date requestTimestamp);

    public StockDTO updateStock(StockDTO updateStockDTO);
}
