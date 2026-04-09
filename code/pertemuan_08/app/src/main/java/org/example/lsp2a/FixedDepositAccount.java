package org.example.lsp2a;

public class FixedDepositAccount extends BankAccount {
    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals not allowed before maturity");
    }
}
