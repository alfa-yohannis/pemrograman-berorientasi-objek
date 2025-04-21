// src/main/java/org/example/srp1b/Invoice.java
package org.example.srp1b;

import java.util.List;

public class Invoice {
    private List<Item> items;

    public Invoice(List<Item> items) {
        this.items = items;
    }

    public double calculateTotal() {
        return items.stream()
                    .mapToDouble(Item::getTotal)
                    .sum();
    }
}
