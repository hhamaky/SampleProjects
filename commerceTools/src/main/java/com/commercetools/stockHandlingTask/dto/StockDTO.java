package com.commercetools.stockHandlingTask.dto;

import java.util.Date;

public class StockDTO extends BaseStockDTO {

    public String productId;

    public StockDTO() {
        super();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public StockDTO(String id, String productId,Date timestamp, Integer quantity) {
        super(id, timestamp, quantity);
        this.productId = productId;
    }
}
