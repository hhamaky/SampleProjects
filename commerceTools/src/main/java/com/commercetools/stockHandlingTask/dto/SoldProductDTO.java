package com.commercetools.stockHandlingTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SoldProductDTO {

    private String productId;

    @JsonProperty("itemsSold")
    private Integer itemsSold;

    public SoldProductDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Integer itemsSold) {
        this.itemsSold = itemsSold;
    }
}
