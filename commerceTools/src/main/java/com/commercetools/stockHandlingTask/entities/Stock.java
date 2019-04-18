package com.commercetools.stockHandlingTask.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @Column(unique = true)
    private String id;

    @Column(name = "TIME_STAMP")
    private Date timestamp;

    @Column
    private String productId;

    @Column
    private Integer quantity;

    @Column(name = "VERSION", columnDefinition="Integer(10) default '0'")
    @Version
    private Integer version;

    public Stock() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Stock(String productId) {
        this.productId = productId;
    }

    public Stock(String id, Integer quantity, String productId, Date timestamp) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
