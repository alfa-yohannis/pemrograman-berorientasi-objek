package org.example.srp1a;

import java.util.List;

public class Invoice {
    private final List<Item> items;

    public Invoice(List<Item> items) {
        this.items = List.copyOf(items);
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public void printInvoice() {
        calculateTotal();
    }

    public void saveToFile() {
        calculateTotal();
    }
}
