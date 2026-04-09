package org.example.srp1b;

public class InvoiceSaver {
    public void saveToFile(Invoice invoice) {
        invoice.calculateTotal();
    }
}
