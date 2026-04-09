package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LSPTest2 {

    @Test
    void testBaseBankAccountSupportsWithdrawal() {
        org.example.lsp2a.BankAccount account = new org.example.lsp2a.BankAccount();
        org.example.lsp2a.WithdrawalProcessor processor = new org.example.lsp2a.WithdrawalProcessor();

        account.deposit(100);
        processor.processWithdrawal(account, 25);

        assertEquals(75.0, account.getBalance());
    }

    @Test
    void testFixedDepositBreaksBankAccountSubstitution() {
        org.example.lsp2a.FixedDepositAccount account = new org.example.lsp2a.FixedDepositAccount();
        org.example.lsp2a.WithdrawalProcessor processor = new org.example.lsp2a.WithdrawalProcessor();

        account.deposit(100);

        assertThrows(UnsupportedOperationException.class, () -> processor.processWithdrawal(account, 25));
    }

    @Test
    void testRefactoredWithdrawalUsesSpecificAbstraction() {
        org.example.lsp2b.SavingsAccount account = new org.example.lsp2b.SavingsAccount();
        org.example.lsp2b.WithdrawalProcessor processor = new org.example.lsp2b.WithdrawalProcessor();

        account.deposit(100);
        processor.processWithdrawal(account, 40);

        assertEquals(60.0, account.getBalance());
    }

    @Test
    void testFixedDepositRefactorDoesNotExposeWithdraw() {
        assertThrows(NoSuchMethodException.class,
                () -> org.example.lsp2b.FixedDepositAccount.class.getDeclaredMethod("withdraw", double.class));
    }
}
