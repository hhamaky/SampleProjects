package com.commercetools.stockHandlingTask.dto;

import java.util.Date;

public class BaseStockDTO {

    public String id;

    public Date timestamp;

    public Integer quantity;

    public BaseStockDTO() {
    }

    public BaseStockDTO(String id, Date timestamp, Integer quantity) {
        this.id = id;
        this.timestamp = timestamp;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
