package MiniBank.src.service;

import java.util.ArrayList;
import java.util.List;

import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;

public class ExecutorDemo {
    public static void main(String[] args) {
        Account account = new SavingsAccount("Batch User", 10000);

        List<Runnable> batch = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            batch.add(() -> {
                try {
                    account.deposit(100);
                    account.withdraw(100);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        try (TransactionProcessor processor = new TransactionProcessor()) {
            processor.submitBatch(batch);
        }

        System.out.println("Batch processing completed.");
        System.out.println("Final balance: " + account.getBalance());
    }
}
