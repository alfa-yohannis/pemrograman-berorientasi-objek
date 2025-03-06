package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailPK id;

    @ManyToOne
    @MapsId("orderCode")
    @JoinColumn(name = "code", nullable = false)
    private Order order;

    @Column(name = "itemcode", length = 5, nullable = false)
    private String itemCode;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    public OrderDetail() {}

    public OrderDetail(OrderDetailPK id, Order order, String itemCode, String name, double price, double quantity) {
        this.id = id;
        this.order = order;
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    
    // Getters and setters
    public OrderDetailPK getId() {
        return id;
    }

    public void setId(OrderDetailPK id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
