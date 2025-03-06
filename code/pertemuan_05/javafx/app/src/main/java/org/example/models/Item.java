package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
  @Id
  @Column(name = "code", length = 5, nullable = false)
  private String code;

  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "quantity", nullable = false)
  private Double quantity;

  // Getters and setters
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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