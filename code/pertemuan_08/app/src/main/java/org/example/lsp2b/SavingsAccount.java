package org.example.lsp2b;

public class SavingsAccount implements WithdrawableAccount {
    private double balance;

    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
