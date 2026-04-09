package org.example.lsp2a;

public class WithdrawalProcessor {
    public void processWithdrawal(BankAccount account, double amount) {
        account.withdraw(amount);
    }
}
