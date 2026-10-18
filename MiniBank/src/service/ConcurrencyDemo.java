package MiniBank.src.service;

import MiniBank.src.model.Account;
import MiniBank.src.model.SavingsAccount;

public class ConcurrencyDemo {
    public static void main(String[] args) {
        Account account = new SavingsAccount("Test User", 10000);

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(
                new AccountWorker(account, 100),
                "Worker-" + (i + 1)
            );
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread interrupted.");
                return;
            }
        }

        System.out.println("All threads completed.");
        System.out.println("Final balance: " + account.getBalance());
    }
}
