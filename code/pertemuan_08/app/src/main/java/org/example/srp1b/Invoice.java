package org.example.srp1b;

import java.util.List;

public class Invoice {
    private final List<Item> items;

    public Invoice(List<Item> items) {
        this.items = List.copyOf(items);
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(Item::getTotal)
                .sum();
    }
}
