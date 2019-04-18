package com.commercetools.stockHandlingTask.dto;

import java.util.Date;

public class ProductStockDTO {

    private String productId;

    private Date requestTimestamp;

    private BaseStockDTO stock;

    protected ProductStockDTO() {
    }

    public ProductStockDTO(String productId, Date requestTimestamp, BaseStockDTO stock) {
        this.productId = productId;
        this.requestTimestamp = requestTimestamp;
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public BaseStockDTO getStock() {
        return stock;
    }

    public void setStock(BaseStockDTO stock) {
        this.stock = stock;
    }
}
