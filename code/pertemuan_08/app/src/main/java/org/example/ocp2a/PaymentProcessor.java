package org.example.ocp2a;

public class PaymentProcessor {

    public void process(String paymentType) {
        if (paymentType.equals("CreditCard")) {
            // Proses pembayaran dengan kartu kredit
        } else if (paymentType.equals("BankTransfer")) {
            // Proses pembayaran dengan transfer bank
        }
    }
}
