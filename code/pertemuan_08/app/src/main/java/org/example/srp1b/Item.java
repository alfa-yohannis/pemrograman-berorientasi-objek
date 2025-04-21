// src/main/java/org/example/srp1b/Item.java
package org.example.srp1b;

public class Item {
    private double price;
    private int quantity;

    public Item(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotal() {
        return price * quantity;
    }
}
