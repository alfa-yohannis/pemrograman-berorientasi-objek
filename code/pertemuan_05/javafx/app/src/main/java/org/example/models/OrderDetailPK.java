package org.example.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailPK implements java.io.Serializable {
    @Column(name = "code", length = 5, nullable = false)
    private String orderCode;

    @Column(name = "line", nullable = false)
    private Integer line;

    public OrderDetailPK() {}

    public OrderDetailPK(String orderCode, int line) {
        this.orderCode = orderCode;
        this.line = line;
    }

    // Getters, setters, equals, and hashCode
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailPK that = (OrderDetailPK) o;
        return Objects.equals(orderCode, that.orderCode) && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderCode, line);
    }
}