package com.commercetools.stockHandlingTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class StatisticsDTO {
    private Date requestTimestamp;

    private String range;

    @JsonProperty("topAvailableProducts")
    private List<StockDTO> topAvailableProducts;

    @JsonProperty("topSellingProducts")
    private List<SoldProductDTO> topSellingProducts;

    public StatisticsDTO() {
    }
    public StatisticsDTO(Date requestTimestamp, String range, List<StockDTO> topAvailableProducts, List<SoldProductDTO> topSellingProducts) {
        this.requestTimestamp = requestTimestamp;
        this.range = range;
        this.topAvailableProducts = topAvailableProducts;
        this.topSellingProducts = topSellingProducts;
    }

    public Date getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Date requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<StockDTO> getTopAvailableProducts() {
        return topAvailableProducts;
    }

    public void setTopAvailableProducts(List<StockDTO> topAvailableProducts) {
        this.topAvailableProducts = topAvailableProducts;
    }

    public List<SoldProductDTO> getTopSellingProducts() {
        return topSellingProducts;
    }

    public void setTopSellingProducts(List<SoldProductDTO> topSellingProducts) {
        this.topSellingProducts = topSellingProducts;
    }


}
