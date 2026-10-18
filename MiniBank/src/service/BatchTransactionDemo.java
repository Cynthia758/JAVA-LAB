
package MiniBank.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;
import MiniBank.src.exception.InvalidAmountException;
import MiniBank.src.exception.InsufficientFundsException;

public class BatchTransactionDemo {

    public static void main(String[] args) {
        Account account = new SavingsAccount("Vaidehi", 10000);
        List<Runnable> batch = new ArrayList<>();
        AtomicLong failures = new AtomicLong();

        for (int i = 0; i < 20; i++) {
            batch.add(() -> {
                try {
                    account.deposit(100);
                } catch (InvalidAmountException e) {
                    failures.incrementAndGet();
                }
            });
        }

        try (TransactionProcessor processor = new TransactionProcessor()) {
            processor.submitBatch(batch);
        }

        long expectedBalance = 10000 + (20 * 100);

        System.out.println("Account: " + account);
        System.out.println("Expected balance: Rs. " + expectedBalance);
        System.out.println("Actual balance: Rs. " + account.getBalance());
        System.out.println("Failed transactions: " + failures.get());

        if (account.getBalance() == expectedBalance && failures.get() == 0) {
            System.out.println("Batch processing test passed.");
        } else {
            System.out.println("Batch processing test failed.");
        }
    }
}
