package org.example.lsp2b;

public class WithdrawalProcessor {
    public void processWithdrawal(WithdrawableAccount account, double amount) {
        account.withdraw(amount);
    }
}
