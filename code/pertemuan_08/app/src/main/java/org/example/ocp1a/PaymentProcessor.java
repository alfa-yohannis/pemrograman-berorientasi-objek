package org.example.ocp1a;

public class PaymentProcessor {

    public void process(String paymentType) {
        if ("CreditCard".equals(paymentType)) {
            return;
        } else if ("BankTransfer".equals(paymentType)) {
            return;
        }

        throw new IllegalArgumentException("Metode pembayaran tidak didukung");
    }
}
